package com.example.communitywaterqualitymonitoring.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.communitywaterqualitymonitoring.R
import com.example.communitywaterqualitymonitoring.ui.theme.CommunityWaterQualityMonitoringTheme

@Composable
fun WikiScreen() {
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Educational Wiki",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            WikiCard(
                title = "The Western Ghats (Sahyadri)",
                content = "The Western Ghats are a mountain range that covers an area of 160,000 km2 in a stretch of 1,600 km parallel to the western coast of the Indian peninsula. It is a UNESCO World Heritage Site and is one of the eight 'hottest hot-spots' of biological diversity in the world."
            )

            WikiCard(
                title = "Importance of Sacred Springs",
                content = "Sacred springs are vital sources of clean drinking water for local communities. They are often associated with local deities, which historically helped in their conservation. Maintaining their purity is essential for the health of the entire ecosystem downstream."
            )

            WikiCard(
                title = "Water Quality Indicators",
                content = "1. Clarity: Turbid water often indicates soil erosion or upstream pollution.\n2. Flow: Changes in flow can signal deforestation or excessive water extraction.\n3. Smell: Unusual odors are often the first sign of industrial or sewage contamination."
            )
        }
    }
}

@Composable
fun WikiCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WikiPreview() {
    CommunityWaterQualityMonitoringTheme {
        WikiScreen()
    }
}
