package com.edu.uniandes.fud.viewModel.login

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.ForgotPWActivity
import com.edu.uniandes.fud.HomeActivity
import com.edu.uniandes.fud.RegisterActivity
import com.edu.uniandes.fud.domain.User
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.Dispatchers
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

	private val _allUsers = MutableLiveData<List<User>>()
	val allUsers: LiveData<List<User>> = _allUsers
	
	private val _usernameAuth = MutableLiveData<String>()
	val usernameAuth: LiveData<String> = _usernameAuth
	
	private val _passwordAuth = MutableLiveData<String>()
	val passwordAuth: LiveData<String> = _passwordAuth
	
	val dbRepository = repository
	
	
	init {
		viewModelScope.launch() {
			repository.refreshData()
			while (true) {
				delay(1000) // Retraso de 1 segundo (1000 milisegundos)
				val users = repository.users.first()
				_allUsers.value = users
				if (users.isNotEmpty()) {
					Log.d("XD_login", "Users: ${_allUsers.value}")
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
		_loginEnable.value = isValidUser(email, password)
	}

	private fun isValidUser(email: String?, password: String?): Boolean {
		return _allUsers.value?.any { it.username == email && it.password == password } ?: false
	}
	
	fun onLoginSelected() {
		if (isValidUser(_email.value, _password.value)) {
			if (ContextCompat.checkSelfPermission(
					context,
					Manifest.permission.ACCESS_FINE_LOCATION
				) != PackageManager.PERMISSION_GRANTED
			) {

				requestLocationPermission()
			} else {
				val intent = Intent(context, HomeActivity::class.java)
				var currentUser = allUsers.value?.first{ it.username == _email.value }
				intent.putExtra("userId", currentUser?.id.toString())
				context.startActivity(intent)
			}
		} else {
			Toast.makeText(
				context,
				"Incorrect username or password",
				Toast.LENGTH_SHORT
			).show()
		}
	}
	
	fun onForgotPWSelected() {
		val intent = Intent(context, ForgotPWActivity::class.java)
		context.startActivity(intent)
	}
	
	fun onRegisterSelected() {
		val intent = Intent(context, RegisterActivity::class.java)
		context.startActivity(intent)
	}

	private fun requestLocationPermission() {
		if(! ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)){
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
