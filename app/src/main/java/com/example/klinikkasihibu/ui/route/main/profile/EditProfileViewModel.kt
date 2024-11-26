package com.example.klinikkasihibu.ui.route.main.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikkasihibu.data.model.User
import com.example.klinikkasihibu.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            val user = userRepository.observeCurrentUser().firstOrNull()
            if (user != null) {
                _state.value = _state.value.copy(
                    name = user.name,
                    role = user.role,
                    image = user.imageUrl
                )
                _user.value = user
            }
        }
    }

    fun onNameChange(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun onRoleChange(role: String) {
        _state.value = _state.value.copy(role = role)
    }

    fun onImagePicked(uri: Uri) {
        _state.value = _state.value.copy(image = uri)
    }

    fun onSaveClick() {
        _state.update { old -> old.copy(isLoading = true) }
        viewModelScope.launch {
            val user = _user.value ?: return@launch
            val name = _state.value.name
            val role = _state.value.role
            val imageUrl: String? = when (val image = _state.value.image) {
                is String -> image
                is Uri -> userRepository.uploadUserImage(image)
                else -> null
            }
            val updatedUser = user.copy(name = name, role = role, imageUrl = imageUrl)
            userRepository.upsertUser(updatedUser)
            val request = UserProfileChangeRequest.Builder()
                .setPhotoUri(imageUrl?.let { Uri.parse(it) })
                .build()
            firebaseAuth.currentUser?.updateProfile(request)
            _state.update { old -> old.copy(isLoading = false, message = "Profile updated") }
        }
    }
}