package com.example.communitywaterqualitymonitoring.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.communitywaterqualitymonitoring.data.FlowRate
import com.example.communitywaterqualitymonitoring.data.OdorLevel
import com.example.communitywaterqualitymonitoring.data.ReportStatus

@Entity(tableName = "local_reports")
data class LocalWaterReport(
    @PrimaryKey val reportId: String,
    val userId: String,
    val locationId: String,
    val clarity: Int,
    val flowRate: FlowRate,
    val odorLevel: OdorLevel,
    val healthScore: Float,
    val status: ReportStatus,
    val remarks: String,
    val reportedAt: Long,
    val imageUrl: String? = null,
    val isSynced: Boolean = false
)

@Entity(tableName = "local_locations")
data class LocalLocation(
    @PrimaryKey val locationId: String,
    val waterBodyName: String,
    val latitude: Double,
    val longitude: Double,
    val district: String,
    val taluk: String,
    val village: String?,
    val riverStreamName: String?
)
