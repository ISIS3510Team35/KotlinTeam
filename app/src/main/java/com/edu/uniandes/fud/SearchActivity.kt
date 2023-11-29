package com.edu.uniandes.fud

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.edu.uniandes.fud.ui.search.SearchScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.search.SearchViewModel
import com.edu.uniandes.fud.viewModel.search.SearchViewModelFactory
import kotlinx.coroutines.launch

class SearchActivity : ComponentActivity() {
    
    private var startTime: Long = 0
    
    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory((application as FuDApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel.setActivity(this.application)
        searchViewModel.setInitialQuery(this.intent.getStringExtra("query").orEmpty())
        setContent {
            MobileAppTheme {
                SearchScreen(searchViewModel)
            }
        }
    }
    
    override fun onStart() {
        super.onStart()
        startTime = System.currentTimeMillis()
    }
    
    override fun onStop() {
        super.onStop()
        // Guarda la marca de tiempo cuando la pantalla se detiene
        val endTime = System.currentTimeMillis()
        // Calcula la duración de la actividad
        val duration = endTime - startTime
        var context : Context = this.applicationContext
        
        lifecycleScope.launch {
            com.edu.uniandes.fud.network.FudNetService.sendTimeSpent(
                duration,
                "Search Screen",
                context
            )
        }
    }



}