package com.edu.uniandes.fud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.edu.uniandes.fud.ui.search.SearchScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewmodel.search.SearchViewModel
import com.edu.uniandes.fud.viewmodel.search.SearchViewModelFactory

class SearchActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory((application as FuDApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileAppTheme {
                SearchScreen(searchViewModel)
            }
        }
    }

}