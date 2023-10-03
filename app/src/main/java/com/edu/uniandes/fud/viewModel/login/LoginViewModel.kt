package com.edu.uniandes.fud.viewModel.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edu.uniandes.fud.model.User
import com.edu.uniandes.fud.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	/* Conexion con BACK
	val repository: UserRepository
	*/
) : ViewModel() {
	
	private val _email = MutableLiveData<String>()
	val email : LiveData<String> = _email
	
	private val _password = MutableLiveData<String>()
	val password : LiveData<String> = _password
	
	private val _loginEnable = MutableLiveData<Boolean>()
	val loginEnable : LiveData<Boolean> = _loginEnable
	
	private val _user = MutableLiveData<List<User>>()
	val user : LiveData<List<User>>
		get() = _user
	
	/* Conexion BACK
	fun getUsers() {
		_user.value = repository.getUsers()
	}
	*/
	
	fun onLoginChanged(email: String, password: String) {
		_email.value = email
		_password.value = password
		_loginEnable.value = isValidEmail(email) && isValidPassword(password)
	}
	
	private fun isValidPassword(password: String): Boolean = password.length > 6
	
	private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
	
	
	fun onLoginSelected() {
		//TODO("Not yet implemented")
		
	}
}