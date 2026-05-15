package com.example.communitywaterqualitymonitoring.data

import com.google.firebase.firestore.GeoPoint
import java.util.Date

enum class UserRole { USER, ADMIN }
enum class ReportStatus { PENDING, VERIFIED, REJECTED }
enum class FlowRate { LOW, MEDIUM, HIGH }
enum class OdorLevel { NONE, MILD, STRONG }
enum class AlertType { POLLUTION, LOW_FLOW, HIGH_RISK, OTHER }

data class User(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val role: UserRole = UserRole.USER,
    val createdAt: Date = Date(),
    val isActive: Boolean = true
)

data class WaterReport(
    val reportId: String = "",
    val userId: String = "",
    val locationId: String = "",
    val clarity: Int = 0, // 1 to 5
    val flowRate: FlowRate = FlowRate.LOW,
    val odorLevel: OdorLevel = OdorLevel.NONE,
    val healthScore: Float = 0f,
    val status: ReportStatus = ReportStatus.PENDING,
    val remarks: String = "",
    val reportedAt: Date = Date(),
    val verifiedAt: Date? = null,
    val verifiedBy: String? = null,
    val imageUrl: String? = null
)

data class Location(
    val locationId: String = "",
    val waterBodyName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val district: String = "",
    val taluk: String = "",
    val village: String? = null,
    val riverStreamName: String? = null,
    val createdAt: Date = Date()
)

data class Alert(
    val alertId: String = "",
    val locationId: String = "",
    val alertType: AlertType = AlertType.OTHER,
    val message: String = "",
    val createdAt: Date = Date(),
    val isRead: Boolean = false
)

data class Stream(
    val id: String = "",
    val name: String = "",
    val path: List<GeoPoint> = emptyList(),
    val healthScore: Int = 0
)
