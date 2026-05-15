package com.example.communitywaterqualitymonitoring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.communitywaterqualitymonitoring.ui.screens.MainScreen
import com.example.communitywaterqualitymonitoring.ui.theme.CommunityWaterQualityMonitoringTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CommunityWaterQualityMonitoringTheme {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    CommunityWaterQualityMonitoringTheme {
        MainScreen()
    }
}
