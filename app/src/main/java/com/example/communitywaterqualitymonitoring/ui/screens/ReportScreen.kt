package com.example.communitywaterqualitymonitoring.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.communitywaterqualitymonitoring.R
import com.example.communitywaterqualitymonitoring.data.*
import com.example.communitywaterqualitymonitoring.ui.theme.CommunityWaterQualityMonitoringTheme
import com.example.communitywaterqualitymonitoring.ui.viewmodels.AuthViewModel
import com.example.communitywaterqualitymonitoring.ui.viewmodels.WaterViewModel
import com.google.android.gms.location.LocationServices
import java.io.File
import java.util.*

@Composable
fun ReportScreen() {
    val waterViewModel: WaterViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val user by authViewModel.user.collectAsState()

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    
    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                    loc?.let {
                        latitude = it.latitude
                        longitude = it.longitude
                    }
                }
            } catch (e: SecurityException) {}
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                loc?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                }
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    ReportContent(
        userId = user?.uid ?: "",
        latitude = latitude,
        longitude = longitude,
        onSubmitReport = { waterViewModel.submitReport(it) }
    )
}

@Composable
fun ReportContent(
    userId: String,
    latitude: Double,
    longitude: Double,
    onSubmitReport: (WaterReport) -> Unit
) {
    var clarity by remember { mutableFloatStateOf(3f) }
    var flowRate by remember { mutableStateOf(FlowRate.MEDIUM) }
    var odorLevel by remember { mutableStateOf(OdorLevel.NONE) }
    var remarks by remember { mutableStateOf("") }
    var waterBodyName by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // File Provider for Camera
    val file = File(context.cacheDir, "temp_image.jpg")
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) imageUri = uri
    }

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Submit Water Report",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = waterBodyName,
                onValueChange = { waterBodyName = it },
                label = { Text("Water Body Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                )
            )

            // Image Upload Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Selected Water Image",
                            modifier = Modifier.size(200.dp).padding(8.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Button(onClick = { cameraLauncher.launch(uri) }) {
                        Icon(Icons.Default.AddAPhoto, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(if (imageUri == null) "Take Photo" else "Retake Photo")
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Clarity (1 to 5)")
                    Slider(
                        value = clarity,
                        onValueChange = { clarity = it },
                        valueRange = 1f..5f,
                        steps = 3
                    )
                    Text("Selected: ${clarity.toInt()}", modifier = Modifier.align(Alignment.End))
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Flow Rate")
                    Row {
                        FlowRate.entries.forEach { rate ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = flowRate == rate, onClick = { flowRate = rate })
                                Text(rate.name.lowercase().replaceFirstChar { it.uppercase() })
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Odor Level")
                    Row {
                        OdorLevel.entries.forEach { level ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = odorLevel == level, onClick = { odorLevel = level })
                                Text(level.name.lowercase().replaceFirstChar { it.uppercase() })
                            }
                        }
                    }
                }
            }

            OutlinedTextField(
                value = remarks,
                onValueChange = { remarks = it },
                label = { Text("Remarks") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                )
            )

            if (latitude != 0.0) {
                Text(
                    String.format(Locale.getDefault(), "Location: %.4f, %.4f", latitude, longitude), 
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    val report = WaterReport(
                        userId = userId,
                        clarity = clarity.toInt(),
                        flowRate = flowRate,
                        odorLevel = odorLevel,
                        remarks = remarks,
                        healthScore = (clarity + (3 - flowRate.ordinal) + (3 - odorLevel.ordinal)) / 3f,
                        imageUrl = imageUri?.toString()
                    )
                    onSubmitReport(report)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = waterBodyName.isNotBlank()
            ) {
                Text("Submit Report")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportPreview() {
    CommunityWaterQualityMonitoringTheme {
        ReportContent(userId = "123", latitude = 13.4125, longitude = 75.2521, onSubmitReport = {})
    }
}
