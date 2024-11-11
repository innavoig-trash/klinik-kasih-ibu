package com.example.klinikkasihibu.ui.route.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.klinikkasihibu.ui.component.PrimaryButton
import com.example.klinikkasihibu.ui.component.topAppBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileRoute(
    toLogin: () -> Unit,
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
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Outlined.AddBox, contentDescription = null)
                    }
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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface),
                        model = user?.imageUrl,
                        contentDescription = null
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(user?.name ?:"Nama Karyawan")
                        Text(user?.role ?: "Role")
                    }
                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Lainnya")
                SettingItem.list.forEach {
                    SettingButton(item = it)
                }
            }
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onLogout() }
            ) {
                Text("Log Out")
            }
        }
    }
}

data class SettingItem(
    val icon: Int,
    val label: String
) {
    companion object {
        val list = listOf(
            SettingItem(R.drawable.language, "Language"),
            SettingItem(R.drawable.tk, "Term and Conditions"),
            SettingItem(R.drawable.help, "Help"),
            SettingItem(R.drawable.contact, "Contact Us"),
        )
    }
}

@Composable
fun SettingButton(
    item: SettingItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(item.icon),
            contentDescription = null
        )
        Text(text = item.label)
    }
}