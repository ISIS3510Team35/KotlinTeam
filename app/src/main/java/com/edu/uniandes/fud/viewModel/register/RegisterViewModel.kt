package com.edu.uniandes.fud.viewModel.register

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
import com.edu.uniandes.fud.HomeActivity
import com.edu.uniandes.fud.domain.User
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class RegisterViewModel(private val context: Context, repository: DBRepository) : ViewModel() {

	private val _email = MutableLiveData<String>()
	val email: LiveData<String> = _email
	
	private val _name = MutableLiveData<String>()
	val name: LiveData<String> = _name
	
	private val _number = MutableLiveData<String>()
	val number: LiveData<String> = _number
	
	private val _password = MutableLiveData<String>()
	val password: LiveData<String> = _password
	
	private val _passwordConfirm = MutableLiveData<String>()
	val passwordConfirm: LiveData<String> = _passwordConfirm
	
	private val _RegisterEnable = MutableLiveData<Boolean>()
	val registerEnable: LiveData<Boolean> = _RegisterEnable

	private val _allUsers = MutableLiveData<List<User>>()
	val allUsers: LiveData<List<User>> = _allUsers
	
	private val _usernameAuth = MutableLiveData<String>()
	val usernameAuth: LiveData<String> = _usernameAuth
	
	private val _passwordAuth = MutableLiveData<String>()
	val passwordAuth: LiveData<String> = _passwordAuth
	
	val dbRepository = repository
	
	
	init {
		viewModelScope.launch {
			repository.refreshData()
			while (true) {
				delay(1000) // Retraso de 1 segundo (1000 milisegundos)
				val users = repository.users.first()
				_allUsers.value = users
				if (users.isNotEmpty()) {
					Log.d("XD_Register", "Users: ${_allUsers.value}")
					break
				} else {
					Log.d("XD_Register", "FSIMA")
				}
			}
		}
	}
	
	enum class ValidationResult {
		Success,
		PasswordMismatch,
		UserFound
	}
	
	fun onRegisterChanged(email: String, name: String, number: String, password: String, passwordConfirm: String) {
		_email.value = email
		_name.value = name
		_number.value = number
		_password.value = password
		_passwordConfirm.value = passwordConfirm
		_RegisterEnable.value = isValidUser(email, name, number, password, passwordConfirm) == ValidationResult.Success
	}
	
	private fun isValidUser(email: String?, name: String?, number: String?, password: String?, passwordConfirm: String?): ValidationResult {
		val user = _allUsers.value?.firstOrNull { it.username == email }
		return when {
			user != null -> ValidationResult.UserFound
			password == passwordConfirm -> ValidationResult.Success
			else -> ValidationResult.PasswordMismatch
		}
	}
	
	
	
	fun onRegisterSelected() {
		if (isValidUser(_email.value, _name.value, _number.value, _password.value, _passwordConfirm.value) == ValidationResult.Success) {
			if (ContextCompat.checkSelfPermission(
					context,
					Manifest.permission.ACCESS_FINE_LOCATION
				) != PackageManager.PERMISSION_GRANTED
			) {
				requestLocationPermission()
			} else {
				newUser(context)
			}
		}
		else if (isValidUser(_email.value, _name.value, _number.value, _password.value, _passwordConfirm.value) == ValidationResult.UserFound) {
			Toast.makeText(
				context,
				"Invalid Username",
				Toast.LENGTH_SHORT
			).show()
		}
		else if (isValidUser(_email.value, _name.value, _number.value, _password.value, _passwordConfirm.value) == ValidationResult.PasswordMismatch) {
			Toast.makeText(
				context,
				"The passwords does not match",
				Toast.LENGTH_SHORT
			).show()
		}
	}
	
	private fun newUser(context: Context) {
		val newUserId = getNextAvailableUserId()
		viewModelScope.launch {
			com.edu.uniandes.fud.network.FudNetService.setUser(
				newUserId,
				_email.value.toString(),
				_name.value.toString(),
				_number.value.toString(),
				_password.value.toString(),
				generateDocumentId(newUserId.toString()),
				context
			)
		}
		Log.d("XD_Register", "Users: ${_allUsers.value}")
		// Continue with your logic, e.g., start the HomeActivity
		val intent = Intent(context, HomeActivity::class.java)
		intent.putExtra("userId", newUserId.toString())
		context.startActivity(intent)
	}
	
	fun generateDocumentId(input: String): String {
		val messageDigest = MessageDigest.getInstance("SHA-256")
		val hashBytes = messageDigest.digest(input.toByteArray(StandardCharsets.UTF_8))
		
		// Convierte el array de bytes del hash a una cadena hexadecimal
		val hexStringBuilder = StringBuilder(2 * hashBytes.size)
		for (byte in hashBytes) {
			val hex = Integer.toHexString(byte.toInt() and 0xFF)
			if (hex.length == 1) {
				hexStringBuilder.append('0')
			}
			hexStringBuilder.append(hex)
		}
		
		return hexStringBuilder.toString()
	}
	
	
	private fun getNextAvailableUserId(): Int {
		val maxUserId = _allUsers.value?.maxByOrNull { it.id }?.id ?: 0
		return maxUserId + 1
	}
	
	
	private fun requestLocationPermission() {
		if(ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)){
			
		}
		else {
			ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 777)
		}
	}
	
}

class RegisterViewModelFactory(private val context: Context, private val repository: DBRepository) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return RegisterViewModel(context, repository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}