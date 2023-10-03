package com.edu.uniandes.fud.di

import com.edu.uniandes.fud.repository.UserRepository
import com.edu.uniandes.fud.repository.UserRepositoryImp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
	
	@Provides
	@Singleton
	fun provideUserRepository(
		database: FirebaseFirestore
	): UserRepository{
		return UserRepositoryImp(database)
	}
}