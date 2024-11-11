package com.example.klinikkasihibu.ui.route.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.klinikkasihibu.ui.component.PrimaryButton

@Composable
fun LoginRoute(
    toSignUp: () -> Unit,
    toMain: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.shouldNavigateToHome) {
        if (state.shouldNavigateToHome) {
            toMain()
        }
        viewModel.onNavigationDone()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.secondary),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Email")
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = {
                            viewModel.onEmailChange(it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Email pengguna") }
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    var showPassword by remember { mutableStateOf(false) }
                    Text("Password")
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = {
                            viewModel.onPasswordChange(it)
                        },
                        visualTransformation = when (showPassword) {
                            true -> VisualTransformation.None
                            false -> PasswordVisualTransformation()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Password") },
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    when (showPassword) {
                                        true -> Icons.Default.VisibilityOff
                                        false -> Icons.Default.Visibility
                                    },
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = "Forgot Password?"
                )
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.isValid(),
                    onClick = { viewModel.onLogin() }
                ) {
                    Text("Login")
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Klinik Utama Kasih Ibu",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}