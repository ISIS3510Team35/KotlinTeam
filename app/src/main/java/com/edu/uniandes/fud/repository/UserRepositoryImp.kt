package com.edu.uniandes.fud.repository

import com.edu.uniandes.fud.model.User
import com.google.firebase.firestore.FirebaseFirestore

class UserRepositoryImp(
	val database: FirebaseFirestore
) : UserRepository{
	
	override fun getUsers(): List<User> {
		return arrayListOf()
	}
}