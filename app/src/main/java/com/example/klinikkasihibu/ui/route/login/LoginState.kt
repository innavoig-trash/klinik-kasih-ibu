package com.example.klinikkasihibu.ui.route.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val shouldNavigateToHome: Boolean = false
) {
    fun isValid() = email.isNotEmpty() && password.isNotEmpty()
}