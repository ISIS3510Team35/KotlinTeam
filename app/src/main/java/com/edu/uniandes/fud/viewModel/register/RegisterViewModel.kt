package com.edu.uniandes.fud.viewModel.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegisterViewModel(repository: DBRepository) : ViewModel() {

	private val _registerEnable = MutableLiveData<Boolean>()
	val registerEnable: LiveData<Boolean> = _registerEnable

	private val _usernamesAuth = MutableLiveData<List<String>>()
	val usernamesAuth: LiveData<List<String>> = _usernamesAuth

	private val _email = MutableLiveData<String>()
	val email: LiveData<String> = _email
	
	private val _password = MutableLiveData<String>()
	val password: LiveData<String> = _password
	
	val dbRepository = repository
	
	

	init {
		viewModelScope.launch {
			repository.users.collect { users ->
				// Update View with the latest favorite news
				_usernamesAuth.value = users.map { it.username }
				
				println("usernameAuth: ${_usernamesAuth.value}")
			}
		}
	}
	
	fun onRegisterChanged(email: String, password: String) {
		_email.value = email
		_password.value = password
		_registerEnable.value = isValidEmail(email) && isValidPassword(password)
	}

	
	fun onRegisterSelected() {
		viewModelScope.launch {
			dbRepository.insertUser(2, _email.value!!, _password.value!!)
		}
	}

	private fun isValidPassword(password: String?): Boolean {
		return password.isNullOrEmpty() || password.length >= 3
	}
	
	private fun isValidEmail(email: String?): Boolean {
		return !email.isNullOrEmpty() || !usernamesAuth.value!!.contains(email)
	}
	
}

class RegisterViewModelFactory(private val repository: DBRepository) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return RegisterViewModel(repository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}
