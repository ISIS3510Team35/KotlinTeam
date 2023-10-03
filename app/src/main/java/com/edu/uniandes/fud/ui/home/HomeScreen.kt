package com.edu.uniandes.fud.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.domain.DishRestaurant
import com.edu.uniandes.fud.ui.theme.Gold
import com.edu.uniandes.fud.ui.theme.MobileAppTheme
import com.edu.uniandes.fud.ui.theme.OrangeSoft
import com.edu.uniandes.fud.ui.theme.Typography
import com.edu.uniandes.fud.viewmodel.home.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel){

    Scaffold (
        containerColor = Color.White,
        topBar = { CustomTopBar() }
    ){ innerPadding ->
        LazyColumn (
            modifier = Modifier.padding(innerPadding)
        ) {
            item{
                SearchBar(viewModel)
            }
            item{
                CarousselMealType(viewModel)
            }
            item {
                CarousselTop3Dish(viewModel)
            }
            item{
                CarousselDishOffers(viewModel)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: HomeViewModel){

    val query: String by viewModel.query.observeAsState(initial = "")

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
            .shadow(5.dp)
            .background(OrangeSoft)
            .height(IntrinsicSize.Min)

    ) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
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
            value = text,
            placeholder = {
                Text(text="Busca tu comida de hoy")
            },
            onValueChange = { newText ->
                text = newText
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
fun CardDish(name : String, restaurantName: String, price: Int, thumbnail: String, id: Int){
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
        Image(
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .padding(10.dp)
                .zIndex(1f)
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(70.dp)
                ),
            painter = painterResource(R.drawable.breakfast),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
fun CardDishOffer(name: String, id: Int, rating: Double, restaurantName: String, oldPrice: Int, newPrice: Int) {
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
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .shadow(
                    elevation = 5.dp,
                ),
            painter = painterResource(R.drawable.breakfast),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
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
                text = oldPrice.toString()+"K",
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
fun CarousselTop3Dish(viewModel: HomeViewModel) {

    val top3Dishes: List<DishRestaurant> by viewModel.top3Dishes.observeAsState(initial = emptyList())

    Text(
        text = "Comida para ti",
        style = Typography.titleMedium,
        modifier = Modifier.padding(horizontal = 17.dp)
    )
    LazyRow {
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
        /*
        items(top3Dishes){
            CardDish(
                name = it.name,
                restaurantName = "central",
                //restaurantName = it.restaurant.name,
                price = it.price,
                thumbnail = it.thumbnail,
                id = it.id
            )
        }
        */

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
            CardTypeMeal("Desayuno", R.drawable.breakfast)
        }
        item {
            CardTypeMeal("Desayuno", R.drawable.breakfast)
        }
        item {
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}

@Composable
fun CarousselDishOffers(viewModel: HomeViewModel) {

    val offerDishes : List<DishRestaurant> by viewModel.offerDishes.observeAsState(initial = emptyList())

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
        /*
        items(offerDishes) {
            CardDishOffer(
                name = it.name,
                id = it.id,
                rating = it.rating,
                restaurantName = "central",
                oldPrice = it.price,
                newPrice = it.newPrice
            )
        }
        */

    }
}