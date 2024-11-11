package com.example.klinikkasihibu.ui.route.main.profile

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikkasihibu.data.model.User
import com.example.klinikkasihibu.data.repository.AbsenRepository
import com.example.klinikkasihibu.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository
): ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _shouldNavigateToLogin = MutableStateFlow(false)
    val shouldNavigateToLogin = _shouldNavigateToLogin.asStateFlow()

    init {
        viewModelScope.launch {
            _user.value = userRepository.fetchCurrentUser()
        }
    }

    fun onLogout() {
        auth.signOut()
        _shouldNavigateToLogin.value = true
    }

    fun onNavigationDone() {
        _shouldNavigateToLogin.value = false
    }
}