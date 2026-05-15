package com.example.communitywaterqualitymonitoring.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.communitywaterqualitymonitoring.R
import com.example.communitywaterqualitymonitoring.data.WaterReport
import com.example.communitywaterqualitymonitoring.ui.theme.CommunityWaterQualityMonitoringTheme
import com.example.communitywaterqualitymonitoring.ui.viewmodels.WaterViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReportHistoryScreen() {
    val viewModel: WaterViewModel = viewModel()
    val reports by viewModel.reports.collectAsState()

    ReportHistoryContent(reports = reports)
}

@Composable
fun ReportHistoryContent(reports: List<WaterReport>) {
    Box(modifier = Modifier.fillMaxSize()) {
        // App background image
        if (!LocalInspectionMode.current) {
            Image(
                painter = painterResource(id = R.drawable.app_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.3f
            )
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "My Report History",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (reports.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No reports submitted yet.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(reports) { report ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Report ID: ${report.reportId.take(8)}...", style = MaterialTheme.typography.titleSmall)
                                    StatusChip(report.status.name)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Clarity: ${report.clarity}/5")
                                Text("Flow: ${report.flowRate}")
                                Text("Odor: ${report.odorLevel}")
                                Spacer(modifier = Modifier.height(4.dp))
                                val dateStr = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(report.reportedAt)
                                Text("Submitted on: $dateStr", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val color = when (status) {
        "VERIFIED" -> MaterialTheme.colorScheme.primary
        "REJECTED" -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.secondary
    }
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small,
        border = androidx.compose.foundation.BorderStroke(1.dp, color)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReportHistoryPreview() {
    CommunityWaterQualityMonitoringTheme {
        ReportHistoryContent(
            reports = listOf(
                WaterReport(reportId = "123456789", clarity = 4),
                WaterReport(reportId = "987654321", clarity = 2)
            )
        )
    }
}
