package com.edu.uniandes.fud.viewModel.list

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.launch
import java.time.LocalTime

@RequiresApi(api = Build.VERSION_CODES.O)
class ListViewModel(private val context: Context, repository: DBRepository) : ViewModel() {

    private val _products = MutableLiveData<List<ProductRestaurant>>()
    val products: LiveData<List<ProductRestaurant>> = _products

    private val _titulo = MutableLiveData<String>()
    val titulo: LiveData<String> = _titulo

    private lateinit var functionList: String

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun checkTimeRange(): Int {
        val time = LocalTime.now()
        Log.d("TIME",time.toString())
        Log.d("TIME",(time in LocalTime.of(4, 0)..LocalTime.of(10, 59)).toString())
        return when (time) {
            in LocalTime.of(4, 0)..LocalTime.of(10, 59) -> 0
            in LocalTime.of(11, 0)..LocalTime.of(14, 59) -> 1
            else -> 2
        }
    }

    fun setFunctionList(pFunctionList : String){
        functionList = pFunctionList
    }

    init {
        viewModelScope.launch {
            // desayuno
            Log.d("XD_TITULO",checkTimeRange().toString())
            when (checkTimeRange()){
                0 -> {
                        _titulo.value = "Desayuno"
                        repository.productsRestaurant.collect { prod ->
                            Log.d("XD_TITULO",prod.filter{ it.category == "Desayuno" }.toString())
                            _products.value = prod.filter{ it.category == "Desayuno" }
                        }


                    }
                1 -> {
                    _titulo.value = "Almuerzo"
                    repository.productsRestaurant.collect { prod ->
                        _products.value = prod.filter { it.category == "Almuerzo" }
                    }

                }
                2 -> {
                    _titulo.value = "Cena"
                    repository.productsRestaurant.collect { prod ->
                        _products.value = prod.filter { it.category == "Cena" }
                    }

                }
            }
            Log.d("XD_TITULO",_titulo.value.toString())
        }
    }
}

@RequiresApi(api = Build.VERSION_CODES.O)
class ListViewModelFactory(private val context: Context, private val repository: DBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(context, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}