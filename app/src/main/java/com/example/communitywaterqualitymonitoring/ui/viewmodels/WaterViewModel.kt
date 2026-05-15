package com.example.communitywaterqualitymonitoring.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.communitywaterqualitymonitoring.data.Alert
import com.example.communitywaterqualitymonitoring.data.WaterReport
import com.example.communitywaterqualitymonitoring.data.WaterRepository
import com.example.communitywaterqualitymonitoring.data.local.WaterDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WaterViewModel(application: Application) : AndroidViewModel(application) {

    private val database = WaterDatabase.getDatabase(application)
    private val repository = WaterRepository(database.waterDao())

    private val _reports = MutableStateFlow<List<WaterReport>>(emptyList())
    val reports: StateFlow<List<WaterReport>> = _reports

    private val _alerts = MutableStateFlow<List<Alert>>(emptyList())
    val alerts: StateFlow<List<Alert>> = _alerts

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        // Observe local database for real-time updates (Step 11: Offline Support)
        viewModelScope.launch {
            repository.allReports.collectLatest { 
                _reports.value = it
            }
        }
        
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                repository.refreshReports()
                _alerts.value = repository.getAlerts()
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun submitReport(report: WaterReport) {
        viewModelScope.launch {
            try {
                repository.submitReport(report)
                // No need to call loadData, repository.allReports flow will update automatically
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
