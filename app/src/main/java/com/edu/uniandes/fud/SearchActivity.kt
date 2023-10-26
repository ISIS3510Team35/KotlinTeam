package com.edu.uniandes.fud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.edu.uniandes.fud.ui.search.SearchScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.search.SearchViewModel
import com.edu.uniandes.fud.viewModel.search.SearchViewModelFactory

class SearchActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory((application as FuDApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel.setInitialQuery(this.intent.getStringExtra("query").orEmpty())
        setContent {
            MobileAppTheme {
                SearchScreen(searchViewModel)
            }
        }
    }



}