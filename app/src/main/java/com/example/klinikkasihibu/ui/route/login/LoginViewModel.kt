package com.example.klinikkasihibu.ui.route.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikkasihibu.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun onLogin() {
        viewModelScope.launch {
            authRepository.login(
                email = _state.value.email,
                password = _state.value.password,
            )
            _state.update { old -> old.copy(shouldNavigateToHome = true) }
        }
    }

    fun onNavigationDone() {
        _state.value = _state.value.copy(shouldNavigateToHome = false)
    }
}