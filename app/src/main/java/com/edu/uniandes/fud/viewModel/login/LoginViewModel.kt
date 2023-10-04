package com.edu.uniandes.fud.viewModel.login

import android.content.Context
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.HomeActivity
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(repository: DBRepository) : ViewModel() {
	
	private val _email = MutableLiveData<String>()
	val email: LiveData<String> = _email
	
	private val _password = MutableLiveData<String>()
	val password: LiveData<String> = _password
	
	private val _loginEnable = MutableLiveData<Boolean>()
	val loginEnable: LiveData<Boolean> = _loginEnable
	
	private val _usernameAuth = MutableLiveData<String>()
	val usernameAuth: LiveData<String> = _usernameAuth
	
	private val _passwordAuth = MutableLiveData<String>()
	val passwordAuth: LiveData<String> = _passwordAuth
	
	val dbRepository = repository
	
	
	init {
		viewModelScope.launch {
			repository.users.collect { users ->
				// Update View with the latest favorite news
				_usernameAuth.value = users[0].username
				_passwordAuth.value = users[0].password
			}
		}
	}
	
	fun onLoginChanged(email: String, password: String) {
		_email.value = email
		_password.value = password
		_loginEnable.value = isValidEmail(email) && isValidPassword(password)
	}
	
	private fun isValidPassword(password: String?): Boolean {
		return password == passwordAuth.value
	}
	
	private fun isValidEmail(email: String?): Boolean {
		return email == usernameAuth.value
	}
	
	fun onLoginSelected() {
	
	}
}

class LoginViewModelFactory(private val repository: DBRepository) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return LoginViewModel(repository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}
