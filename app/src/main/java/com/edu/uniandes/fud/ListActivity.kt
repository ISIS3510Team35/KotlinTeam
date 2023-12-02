package com.edu.uniandes.fud

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.edu.uniandes.fud.ui.list.ListScreen
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.viewModel.list.ListViewModel
import com.edu.uniandes.fud.viewModel.list.ListViewModelFactory


@RequiresApi(api = Build.VERSION_CODES.O)
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