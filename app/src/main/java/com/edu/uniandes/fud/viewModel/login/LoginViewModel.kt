package com.edu.uniandes.fud.viewModel.login

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.HomeActivity
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context, repository: DBRepository) : ViewModel() {
	
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
			while (true) {
				delay(1000) // Retraso de 5 segundos (5000 milisegundos)
				val users = repository.users.first()
				if (users.isNotEmpty()) {
					_usernameAuth.value = users[0].username
					_passwordAuth.value = users[0].password
					Log.d("XD_login", "usernameAuth: ${_usernameAuth.value}")
					Log.d("XD_login", "passwordAuth: ${_passwordAuth.value}")
					break
				} else {
					Log.d("XD_login", "FSIMA")
				}
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
		if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
			
			requestLocationPermission()
		}
		else {
			context.startActivity(Intent(context, HomeActivity::class.java))
		}
	}

	private fun requestLocationPermission() {
		if(ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)){
			
		}
		else {
			ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 777)
		}
	}
	
	
}

class LoginViewModelFactory(private val context: Context, private val repository: DBRepository) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return LoginViewModel(context, repository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}
