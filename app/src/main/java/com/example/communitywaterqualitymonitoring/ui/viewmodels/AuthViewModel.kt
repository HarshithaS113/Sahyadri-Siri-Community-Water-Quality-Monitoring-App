package com.example.communitywaterqualitymonitoring.ui.viewmodels

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communitywaterqualitymonitoring.data.AuthRepository
import com.example.communitywaterqualitymonitoring.ui.theme.CommunityWaterQualityMonitoringTheme
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _user = MutableStateFlow<FirebaseUser?>(repository.getCurrentUser())
    val user: StateFlow<FirebaseUser?> = _user

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun signIn(email: String, pass: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                repository.signIn(email, pass)
                _user.value = repository.getCurrentUser()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun signUp(email: String, pass: String, name: String, phone: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                repository.signUp(email, pass, name, phone)
                _user.value = repository.getCurrentUser()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun signOut() {
        repository.signOut()
        _user.value = null
    }
}

/**
 * A stateless component to preview different Auth states managed by the ViewModel.
 */
@Composable
fun AuthStatePreview(
    userEmail: String?,
    loading: Boolean,
    error: String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Auth State Debugger",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            
            Text(text = "User: ${userEmail ?: "Logged Out"}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Loading: $loading", style = MaterialTheme.typography.bodyMedium)
            
            if (error != null) {
                Text(
                    text = "Error: $error",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            } else {
                Text(
                    text = "Error: None",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            if (loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Logged Out")
@Composable
fun PreviewAuthLoggedOut() {
    CommunityWaterQualityMonitoringTheme {
        AuthStatePreview(userEmail = null, loading = false, error = null)
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun PreviewAuthLoading() {
    CommunityWaterQualityMonitoringTheme {
        AuthStatePreview(userEmail = null, loading = true, error = null)
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun PreviewAuthError() {
    CommunityWaterQualityMonitoringTheme {
        AuthStatePreview(userEmail = null, loading = false, error = "Invalid credentials. Please try again.")
    }
}

@Preview(showBackground = true, name = "Logged In")
@Composable
fun PreviewAuthLoggedIn() {
    CommunityWaterQualityMonitoringTheme {
        AuthStatePreview(userEmail = "harshitha@example.com", loading = false, error = null)
    }
}
