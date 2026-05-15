package com.example.communitywaterqualitymonitoring.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {
    @Query("SELECT * FROM local_reports ORDER BY reportedAt DESC")
    fun getAllReports(): Flow<List<LocalWaterReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: LocalWaterReport)

    @Query("SELECT * FROM local_reports WHERE isSynced = 0")
    suspend fun getUnsyncedReports(): List<LocalWaterReport>

    @Update
    suspend fun updateReport(report: LocalWaterReport)

    @Query("SELECT * FROM local_locations")
    fun getAllLocations(): Flow<List<LocalLocation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocalLocation)
}
