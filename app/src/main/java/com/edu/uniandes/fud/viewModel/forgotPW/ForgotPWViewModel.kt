package com.edu.uniandes.fud.viewModel.forgotPW

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.LoginActivity
import com.edu.uniandes.fud.domain.User
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ForgotPWViewModel(private val context: Context, repository: DBRepository) : ViewModel() {
	
	private val _documentId = MutableLiveData<String>()
	val documentId: LiveData<String> = _documentId
	
	private val _email = MutableLiveData<String>()
	val email: LiveData<String> = _email
	
	private val _password = MutableLiveData<String>()
	val password: LiveData<String> = _password
	
	private val _passwordConfirm = MutableLiveData<String>()
	val passwordConfirm: LiveData<String> = _passwordConfirm
	
	private val _ForgotPWEnable = MutableLiveData<Boolean>()
	val forgotPWEnable: LiveData<Boolean> = _ForgotPWEnable
	
	private val _allUsers = MutableLiveData<List<User>>()
	val allUsers: LiveData<List<User>> = _allUsers
	
	private val _usernameAuth = MutableLiveData<String>()
	val usernameAuth: LiveData<String> = _usernameAuth
	
	private val _passwordAuth = MutableLiveData<String>()
	val passwordAuth: LiveData<String> = _passwordAuth
	
	private val _passwordConfirmAuth = MutableLiveData<String>()
	val passwordConfirmAuth: LiveData<String> = _passwordConfirmAuth
	
	
	init {
		viewModelScope.launch {
			repository.refreshData()
			while (true) {
				delay(1000) // Retraso de 1 segundo (1000 milisegundos)
				val users = repository.users.first()
				_allUsers.value = users
				if (users.isNotEmpty()) {
					Log.d("XD_ForgotPW", "Passwords: ${_allUsers.value}")
					break
				} else {
					Log.d("XD_ForgotPW", "FSIMA")
				}
			}
		}
	}
	
	enum class ValidationResult {
		Success,
		PasswordMismatch,
		NotUnique,
		UserNotFound
	}
	
	fun onForgotPWChanged(email: String, password: String, passwordConfirm: String) {
		_email.value = email
		_password.value = password
		_passwordConfirm.value = passwordConfirm
		_ForgotPWEnable.value = isValidPassword(email, password, passwordConfirm) == ValidationResult.Success
	}
	
	private fun isValidPassword(username: String?, password: String?, confirmPassword: String?): ValidationResult {
		_allUsers.value?.firstOrNull { it.username == username }?.let { user ->
			return when {
				user.password == password -> ValidationResult.NotUnique
				password != confirmPassword -> ValidationResult.PasswordMismatch
				else -> ValidationResult.Success
			}
		}
		return ValidationResult.UserNotFound
	}


	
	fun onForgotPWSelected() {
		if (isValidPassword(_email.value, _password.value, _passwordConfirm.value) == ValidationResult.Success) {
			updateUser(context)
		}
		else if (isValidPassword(_email.value, _password.value, _passwordConfirm.value) == ValidationResult.UserNotFound) {
			Toast.makeText(
				context,
				"Incorrect username",
				Toast.LENGTH_SHORT
			).show()
		}
		else if (isValidPassword(_email.value, _password.value, _passwordConfirm.value) == ValidationResult.NotUnique) {
			Toast.makeText(
				context,
				"The password entered needs to be different",
				Toast.LENGTH_SHORT
			).show()
		}
		else if (isValidPassword(_email.value, _password.value, _passwordConfirm.value) == ValidationResult.PasswordMismatch) {
			Toast.makeText(
				context,
				"The passwords does not match",
				Toast.LENGTH_SHORT
			).show()
		}
	}
	
	private fun updateUser(context: Context) {
		val user = getUserDocumentId()
		
		if (user != null) {
			viewModelScope.launch {
				com.edu.uniandes.fud.network.FudNetService.updateUser(
					user.id,
					user.username,
					user.name,
					user.number,
					_password.value ?: "",
					user.documentId,
					context
				)
			}
			// Continue with your logic, e.g., start the HomeActivity
			val intent = Intent(context, LoginActivity::class.java)
			context.startActivity(intent)
		} else {
			// Handle the case where the user is null (not found)
			Log.e("XD_Register", "User not found for email: ${_email.value}")
			// You might want to show a message to the user or handle it in some way
		}
	}
	
	fun getUserDocumentId(): User? {
		// Get the current user's ID based on the entered email
		return allUsers.value?.firstOrNull { it.username == _email.value }
	}


	
}

class ForgotPWViewModelFactory(private val context: Context, private val repository: DBRepository) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(ForgotPWViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return ForgotPWViewModel(context, repository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}