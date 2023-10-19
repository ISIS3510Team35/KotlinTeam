package com.edu.uniandes.fud.ui.home

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.SearchActivity
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.ui.theme.Gold
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.ui.theme.OrangeSoft
import com.edu.uniandes.fud.ui.theme.Typography
import com.edu.uniandes.fud.viewmodel.home.HomeViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel){

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
            }
            item{
                CarousselMealType(viewModel)
            }
            item {
                CarousselTop3Product(viewModel)
            }
            item{
                CarousselProductOffers(viewModel)
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


@Composable
fun TopBarPreview() {
    MobileAppTheme {
        CustomTopBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(viewModel: HomeViewModel, context: Context){


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

                // cambio a query
                val intent = Intent(context, SearchActivity::class.java)
                intent.putExtra("query",query)
                context.startActivity(intent)
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



// Diferentes

@Composable
fun CardProduct(name : String, restaurantName: String, price: Int, image: String, id: Int){
    Box (
        modifier = Modifier.width(220.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .width(220.dp)
                .height(IntrinsicSize.Min)
                .padding(top = 40.dp)
                .padding(10.dp)
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable { },
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Spacer(modifier = Modifier
                .height(100.dp)
                .fillMaxWidth())
            Text(
                text = name,
                style = Typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = restaurantName,
                style = Typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = price.toString()+"K",
                style = Typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        AsyncImage(
            model = image,
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .padding(10.dp)
                .zIndex(1f)
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(70.dp)
                ),
            placeholder = painterResource(R.drawable.loading),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
fun CardProductOffer(name: String, id: Int, rating: Double, restaurantName: String, price: Int, offerPrice: Int, image: String ) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(230.dp)
            .padding(10.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .shadow(
                    elevation = 5.dp,
                )
        ){
            AsyncImage(
                model = image,
                modifier = Modifier
                    .fillMaxSize(),
                placeholder = painterResource(R.drawable.loading),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Column (
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = name,
                style = Typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = price.toString()+"K",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                textAlign = TextAlign.Center,
                style = Typography.labelMedium
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){

                Text(
                    text = rating.toString(),
                    modifier = Modifier
                        .padding(2.dp),
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Gold
                )
            }
        }
    }
}

@Composable
fun CarousselTop3Product(viewModel: HomeViewModel) {

    val top3Products : List<ProductRestaurant> by viewModel.top3Products.observeAsState(initial = emptyList())

    Text(
        text = "Top 3 restaurantes un tu zona",
        style = Typography.titleMedium,
        modifier = Modifier.padding(horizontal = 17.dp)
    )
    LazyRow {
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }

        items(top3Products){
            CardProduct(
                name = it.name,
                restaurantName = it.restaurant.name,
                price = it.price,
                image = it.image,
                id = it.id
            )
        }


    }
}

@Composable
fun CardTypeMeal(title: String, picture: Int) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(150.dp)
            .padding(10.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 15.dp, end = 15.dp)
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .weight(1f)
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(10.dp)
                ),
            painter = painterResource(picture),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun CarousselMealType(viewModel: HomeViewModel) {
    Text(
        text = "Categorías",
        style = Typography.titleMedium,
        modifier = Modifier.padding(horizontal=17.dp)
    )
    LazyRow {
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
        item {
            CardTypeMeal("Desayuno", R.drawable.breakfast)
        }
        item {
            CardTypeMeal("Almuerzo", R.drawable.band_paisa)
        }
        item {
            CardTypeMeal("Cena", R.drawable.cena)
        }
        item {
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}

@Composable
fun CarousselProductOffers(viewModel: HomeViewModel) {

    val offerProducts : List<ProductRestaurant> by viewModel.offerProducts.observeAsState(initial = emptyList())


    Text(
        text = "Las mejores ofertas",
        style = Typography.titleMedium,
        modifier = Modifier
            .padding(top = 5.dp)
            .padding(horizontal = 17.dp)
    )
    LazyRow {
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }


        items(offerProducts) {
            CardProductOffer(
                id = it.id,
                name = it.name,
                rating = it.rating,
                restaurantName = "central",
                price = it.price,
                offerPrice = it.offerPrice,
                image = it.image
            )

        }




    }
}