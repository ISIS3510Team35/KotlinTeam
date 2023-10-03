package com.edu.uniandes.fud.repository

import com.edu.uniandes.fud.model.User

interface UserRepository  {
	
	fun getUsers(): List<User>
}