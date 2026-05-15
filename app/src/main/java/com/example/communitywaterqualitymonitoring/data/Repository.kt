package com.example.communitywaterqualitymonitoring.data

import com.example.communitywaterqualitymonitoring.data.local.LocalWaterReport
import com.example.communitywaterqualitymonitoring.data.local.WaterDao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID

class WaterRepository(private val waterDao: WaterDao) {
    private val db = FirebaseFirestore.getInstance()

    // Get reports from local database for immediate UI updates
    val allReports: Flow<List<WaterReport>> = waterDao.getAllReports().map { localList ->
        localList.map { it.toDomainModel() }
    }

    suspend fun submitReport(report: WaterReport) {
        val reportId = if (report.reportId.isEmpty()) UUID.randomUUID().toString() else report.reportId
        val reportWithId = report.copy(reportId = reportId)
        
        // 1. Save to local DB first (Offline support)
        waterDao.insertReport(reportWithId.toLocalModel(isSynced = false))

        // 2. Try to sync with Firestore
        try {
            db.collection("water_reports").document(reportId).set(reportWithId).await()
            // 3. Update local status if successful
            waterDao.insertReport(reportWithId.toLocalModel(isSynced = true))
        } catch (e: Exception) {
            // Keep as unsynced; background worker or next launch will retry
        }
    }

    suspend fun refreshReports() {
        try {
            val snapshot = db.collection("water_reports").get().await()
            val remoteReports = snapshot.toObjects(WaterReport::class.java)
            remoteReports.forEach { 
                waterDao.insertReport(it.toLocalModel(isSynced = true))
            }
        } catch (e: Exception) {
            // Offline or error, use local data
        }
    }

    suspend fun getAlerts(): List<Alert> {
        return try {
            db.collection("alerts")
                .orderBy("createdAt")
                .get()
                .await()
                .toObjects(Alert::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Helper extensions for mapping
    private fun WaterReport.toLocalModel(isSynced: Boolean) = LocalWaterReport(
        reportId = reportId,
        userId = userId,
        locationId = locationId,
        clarity = clarity,
        flowRate = flowRate,
        odorLevel = odorLevel,
        healthScore = healthScore,
        status = status,
        remarks = remarks,
        reportedAt = reportedAt.time,
        imageUrl = imageUrl,
        isSynced = isSynced
    )

    private fun LocalWaterReport.toDomainModel() = WaterReport(
        reportId = reportId,
        userId = userId,
        locationId = locationId,
        clarity = clarity,
        flowRate = flowRate,
        odorLevel = odorLevel,
        healthScore = healthScore,
        status = status,
        remarks = remarks,
        reportedAt = java.util.Date(reportedAt),
        imageUrl = imageUrl
    )
}
