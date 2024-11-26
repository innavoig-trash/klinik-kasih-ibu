package com.example.klinikkasihibu.ui.route.main.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.klinikkasihibu.ui.component.PrimaryButton
import com.example.klinikkasihibu.ui.component.topAppBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileRoute(
    onBackStack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val user by viewModel.user.collectAsStateWithLifecycle()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { if (it != null) viewModel.onImagePicked(it) }
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors.copy(containerColor = MaterialTheme.colorScheme.background),
                title = {
                    Text("Edit Profile")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackStack
                    ) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (user != null) {
                AsyncImage(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .size(128.dp)
                        .clickable { launcher.launch("image/*") }
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    model = state.image,
                    contentDescription = null
                )
                Text("Upload Image", style = MaterialTheme.typography.titleMedium)
                Column(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp))
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (state.message != null) {
                        Text(state.message!!)
                    }
                    Column {
                        Text("Nama", style = MaterialTheme.typography.titleMedium)
                        OutlinedTextField(
                            value = state.name,
                            onValueChange = viewModel::onNameChange,
                            singleLine = true
                        )
                    }
                    Column {
                        Text("Role", style = MaterialTheme.typography.titleMedium)
                        OutlinedTextField(
                            value = state.role,
                            onValueChange = viewModel::onRoleChange,
                            singleLine = true
                        )
                    }
                    PrimaryButton(
                        modifier = Modifier.align(Alignment.End),
                        enabled = !state.isLoading,
                        onClick = viewModel::onSaveClick
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}