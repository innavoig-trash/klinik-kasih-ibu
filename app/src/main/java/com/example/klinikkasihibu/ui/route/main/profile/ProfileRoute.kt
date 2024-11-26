package com.example.klinikkasihibu.ui.route.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.klinikkasihibu.R
import com.example.klinikkasihibu.ui.component.ErrorButton
import com.example.klinikkasihibu.ui.component.PrimaryButton
import com.example.klinikkasihibu.ui.component.topAppBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileRoute(
    toLogin: () -> Unit,
    toEditProfile: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsStateWithLifecycle()
    val navigation by viewModel.shouldNavigateToLogin.collectAsStateWithLifecycle()

    LaunchedEffect(navigation) {
        if (navigation) {
            toLogin()
        }
        viewModel.onNavigationDone()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(4.dp),
                title = {
                    Text("Profile")
                },
                colors = topAppBarColors
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .size(128.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    model = user?.imageUrl,
                    contentDescription = null
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(user?.name ?:"Nama Karyawan")
                    Text("ID: ${user?.id}" ?: "Role")
                    Text(user?.role ?: "Role")
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { toEditProfile() }
                ) {
                    Text("Edit Profile")
                }
                ErrorButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.onLogout() }
                ) {
                    Text("Log Out")
                }
            }
        }
    }
}