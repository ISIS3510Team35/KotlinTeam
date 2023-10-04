package com.edu.uniandes.fud.ui.search

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.ui.theme.Orange
import com.edu.uniandes.fud.ui.theme.OrangeSoft
import com.edu.uniandes.fud.ui.theme.Typography
import com.edu.uniandes.fud.viewmodel.search.SearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel){

    val context = LocalContext.current

    Scaffold (
        containerColor = Color.White,
        topBar = { CustomTopBar() }
    ){ innerPadding ->
        LazyColumn (
            modifier = Modifier.padding(innerPadding)
        ) {
            item{
                SearchBar(viewModel, context)
                Filter()
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = OrangeSoft),
        navigationIcon = {
            IconButton(
                modifier = Modifier.fillMaxHeight(),
                onClick = { }
            ) {
                Image(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = "dashboard_search"
                )
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
            ){
                Surface(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 5.dp)
                        .height(30.dp),
                    shape = RoundedCornerShape(10.dp),
                    shadowElevation = 5.dp,
                    onClick = { }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        Image(
                            modifier = Modifier.fillMaxHeight(),
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = "dashboard_search"
                        )
                        Image(
                            modifier = Modifier.fillMaxHeight(),
                            painter = painterResource(id = R.drawable.uniandes),
                            contentDescription = "dashboard_search"
                        )
                    }
                }
            }},
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(color = OrangeSoft)
            .padding(horizontal = 12.dp)
            .padding(top = 5.dp)
            .padding(top = 2.dp),
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentDescription = "FuD Logo"
                )
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(viewModel: SearchViewModel, context: Context){


    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val query: String by viewModel.query.observeAsState(initial = "")



    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
            .shadow(5.dp)
            .background(OrangeSoft)
            .height(IntrinsicSize.Min)

    ) {
        TextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .padding(horizontal = 17.dp)
                .padding(vertical = 10.dp)
                .weight(1f)
                .shadow(
                    elevation = 7.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.White),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {

                // Hide the keyboard after submitting the search
                keyboardController?.hide()
                //or hide keyboard
                focusManager.clearFocus()

            }),
            value = query,
            placeholder = {
                Text(text="Busca tu comida de hoy")
            },
            onValueChange = {
                viewModel.onSearchChange(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                cursorColor = Color.Black,
                disabledLabelColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )
        val context = LocalContext.current
        IconButton(
            modifier = Modifier
                .padding(start = 0.dp, top = 10.dp, bottom = 10.dp, end = 17.dp)
                .fillMaxHeight()
                .wrapContentWidth()
                .shadow(
                    elevation = 7.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .aspectRatio(1f),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White
            ),
            onClick = { }
        ) {
            Icon(
                painterResource(id = R.drawable.ic_sliders),
                modifier = Modifier.padding(2.dp),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun Filter(){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .offset(y=(-10).dp)
            .background(Color.White)
            .padding(10.dp)
            .shadow(
                elevation = 0.dp,
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally


    ){
        Text(text = "Filtrar por precio")
        SliderMinimalExample("$1K","$100K")
        Text(text = "Filtrar por mínimo timepo")
        SliderMinimalExample("15min","60min")
        Text(text = "Filtrar por lejanía")
        SliderMinimalExample("10m","100m")
        FilledButtonExample {

        }
    }
}

@Composable
fun SliderMinimalExample(t1: String, t2: String) {

    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = t1,style= Typography.headlineMedium)
        Slider(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = OrangeSoft,
                activeTrackColor = Orange,
                inactiveTrackColor = Orange,
            ),
            steps = 3,
            valueRange = 0f..50f
        )
        Text(text = t2, style= Typography.headlineMedium)
    }
}

@Composable
fun FilledButtonExample(onClick: () -> Unit) {
    Button(onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Orange))
    {
        Text("Aplicar Filtros")
    }
}


