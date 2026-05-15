package com.example.communitywaterqualitymonitoring.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.communitywaterqualitymonitoring.R
import com.example.communitywaterqualitymonitoring.data.Alert
import com.example.communitywaterqualitymonitoring.data.AlertType
import com.example.communitywaterqualitymonitoring.data.WaterReport
import com.example.communitywaterqualitymonitoring.ui.theme.CommunityWaterQualityMonitoringTheme
import com.example.communitywaterqualitymonitoring.ui.viewmodels.WaterViewModel

@Composable
fun DashboardScreen() {
    val viewModel: WaterViewModel = viewModel()
    val reports by viewModel.reports.collectAsState()
    val alerts by viewModel.alerts.collectAsState()

    DashboardContent(reports = reports, alerts = alerts)
}

@Composable
fun DashboardContent(reports: List<WaterReport>, alerts: List<Alert>) {
    Box(modifier = Modifier.fillMaxSize()) {
        // App background image
        if (!LocalInspectionMode.current) {
            Image(
                painter = painterResource(id = R.drawable.app_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.2f
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Overview", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Total Reports: ${reports.size}")
                        Text("Active Alerts: ${alerts.size}")
                    }
                }
            }

            item {
                Text(
                    text = "Recent Alerts",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            if (alerts.isEmpty()) {
                item {
                    Text(
                        text = "No active alerts in your area.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                items(alerts) { alert ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.9f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(alert.alertType.name, style = MaterialTheme.typography.titleSmall)
                            Text(alert.message)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    CommunityWaterQualityMonitoringTheme {
        DashboardContent(
            reports = listOf(WaterReport(), WaterReport()),
            alerts = listOf(
                Alert(alertType = AlertType.POLLUTION, message = "Pollution detected in Stream A"),
                Alert(alertType = AlertType.LOW_FLOW, message = "Low flow reported in Spring B")
            )
        )
    }
}
