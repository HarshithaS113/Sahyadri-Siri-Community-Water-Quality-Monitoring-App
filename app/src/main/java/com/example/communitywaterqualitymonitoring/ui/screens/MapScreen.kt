package com.example.communitywaterqualitymonitoring.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.communitywaterqualitymonitoring.R
import com.example.communitywaterqualitymonitoring.data.Stream
import com.example.communitywaterqualitymonitoring.ui.theme.CommunityWaterQualityMonitoringTheme
import com.example.communitywaterqualitymonitoring.ui.theme.DeepWater
import com.example.communitywaterqualitymonitoring.ui.theme.PollutedBrown
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.*

@Composable
fun MapScreen() {
    val westernGhats = LatLng(13.4125, 75.2521) // Kudremukh area
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(westernGhats, 10f)
    }

    var streams by remember { mutableStateOf<List<Stream>>(emptyList()) }
    val isPreview = LocalInspectionMode.current

    DisposableEffect(Unit) {
        if (isPreview) {
            onDispose {}
        } else {
            val db = FirebaseFirestore.getInstance()
            val registration = db.collection("streams").addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    streams = snapshot.toObjects(Stream::class.java)
                }
            }
            
            onDispose {
                registration.remove()
            }
        }
    }

    MapContent(cameraPositionState = cameraPositionState, streams = streams)
}

@Composable
fun MapContent(cameraPositionState: CameraPositionState, streams: List<Stream>) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (!LocalInspectionMode.current) {
            Image(
                painter = painterResource(id = R.drawable.app_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.3f
            )
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            for (stream in streams) {
                val points = stream.path.map { LatLng(it.latitude, it.longitude) }
                // Polyline requires at least 2 points to render correctly
                if (points.size >= 2) {
                    Polyline(
                        points = points,
                        color = if (stream.healthScore > 3) DeepWater else PollutedBrown,
                        width = 10f,
                        clickable = true
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    CommunityWaterQualityMonitoringTheme {
        MapContent(
            cameraPositionState = rememberCameraPositionState(),
            streams = emptyList()
        )
    }
}
