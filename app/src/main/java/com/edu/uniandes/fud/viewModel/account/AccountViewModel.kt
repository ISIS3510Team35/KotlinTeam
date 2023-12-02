package com.edu.uniandes.fud.viewModel.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.domain.User
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AccountViewModel(repository: DBRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _userId = MutableLiveData<Int>()

    val dbRepository = repository

    fun setInitialUser(userId: Int) {
        _userId.value = userId
    }

    init {
        viewModelScope.launch {
            repository.users.collect { fudUsers ->
                _user.value = fudUsers.find { usr -> _userId.value == usr.id }
            }
        }
    }

}

class AccountViewModelFactory(private val repository: DBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}