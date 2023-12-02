package com.edu.uniandes.fud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.edu.uniandes.fud.ui.list.ListScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.list.ListViewModel
import com.edu.uniandes.fud.viewModel.list.ListViewModelFactory


class ListActivity : ComponentActivity() {

    private val listViewModel: ListViewModel by viewModels {
        ListViewModelFactory(this, (application as FuDApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MobileAppTheme {
                ListScreen(listViewModel)
            }
        }
    }
}