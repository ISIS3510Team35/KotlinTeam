package com.edu.uniandes.fud.ui.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.edu.uniandes.fud.ProductActivity
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.RestaurantActivity
import com.edu.uniandes.fud.domain.Product
import com.edu.uniandes.fud.domain.RestaurantProduct
import com.edu.uniandes.fud.ui.theme.Gold
import com.edu.uniandes.fud.ui.theme.Orange
import com.edu.uniandes.fud.ui.theme.OrangeSoft
import com.edu.uniandes.fud.ui.theme.Typography
import com.edu.uniandes.fud.viewModel.search.SearchViewModel
import kotlin.math.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel){

    val context = LocalContext.current
    val results : List<RestaurantProduct> by viewModel.results.observeAsState(initial = emptyList())

    Scaffold (
        containerColor = Color.White,
        topBar = { CustomTopBar() }
    ){ innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            SearchBar(viewModel, context)
            Filter(viewModel)
            LazyColumn {
                items(results) {
                    ElementSearch(
                        restaurantName = it.name,
                        restaurantAdress = it.name,
                        restaurantImage = it.image,
                        dishList = it.products,
                        restaurantLat = it.location.latitude,
                        restaurantLong = it.location.longitude,
                        context,
                        viewModel
                    )
                }
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
    val visibleFilters by viewModel.visibleFilters.observeAsState(initial = false)
    val colorTintIcon by viewModel.colorTintIcon.observeAsState(initial = Color.Black)
    val colorBackIcon by viewModel.colorBackIcon.observeAsState(initial = Color.White)
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val query: String by viewModel.query.observeAsState(initial = "")
    val context = LocalContext.current
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
            .shadow(5.dp)
            .background(OrangeSoft)
            .height(IntrinsicSize.Min)
            .zIndex(39F)

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
                containerColor = colorBackIcon
            ),
            onClick = {
                viewModel.toggleFilter()
            }
        ) {
            Icon(
                painterResource(id = R.drawable.ic_sliders),
                modifier = Modifier.padding(2.dp),
                tint = colorTintIcon,
                contentDescription = null
            )
        }
    }
}

@Composable
fun Filter(viewModel: SearchViewModel){

    val visibleFilters by viewModel.visibleFilters.observeAsState(initial = false)
    val veggieBoolean by viewModel.veggieBoolean.observeAsState(initial = false)
    val veganBoolean by viewModel.veganBoolean.observeAsState(initial = false)
    val context = LocalContext.current
    AnimatedVisibility(visibleFilters) {
        Box(
            modifier = Modifier
                .zIndex(0F)
                .offset(0.dp, (-22).dp)
                .fillMaxWidth()
                .background(Color.White)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                )

            //.border(1.dp, Color.Black, shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            )
            {
                Text(text = "Filtrar por precio")
                SliderMinimalExample("$1K", "$100K", viewModel)
                Text(text = "Filtrar por tipo de comida")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = veggieBoolean,
                        onCheckedChange = { isChecked -> viewModel.onChangeVeggieFilter(isChecked) }
                    )
                    Text(text = "Vegetariano", style = Typography.bodyLarge)
                    Checkbox(
                        checked = veganBoolean,
                        onCheckedChange = { isChecked -> viewModel.onChangeVeganFilter(isChecked) }
                    )
                    Text(text = "Vegano", style = Typography.bodyLarge)
                }


                FilledButtonExample {
                    viewModel.applyFilters(context)
                }
            }

        }
    }
}

@Composable
fun SliderMinimalExample(t1: String, t2: String, viewModel: SearchViewModel) {

    val sliderPosition by viewModel.pricePosition.observeAsState(initial = 0f)

    Row (
        modifier = Modifier.fillMaxWidth()
    ){
        Text(text = t1,style= Typography.headlineMedium)
        Slider(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            value = sliderPosition,
            onValueChange = { viewModel.onChangePriceFilter(it) },
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

//dishId: Int, dishName: String, dishRating: Double, dishPrice: Double, dishImage: String
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElementSearch(restaurantName: String, restaurantAdress: String, restaurantImage: String, dishList: List<Product>, restaurantLat: Double, restaurantLong: Double, context: Context, viewModel: SearchViewModel){

    val location by viewModel.myLocation.observeAsState(initial = null)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        onClick = {
            val intent = Intent(context, RestaurantActivity::class.java)
            context.startActivity(intent)
        }

    ){
        Column () {
            Row (
                modifier = Modifier
                    .padding(10.dp, 10.dp, 10.dp, 0.dp)
                    .height(120.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = restaurantImage,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    verticalArrangement = Arrangement.Center
                ){
                    Text(text = restaurantName, style= Typography.headlineMedium)
                    if(location!=null){
                        var metros = calculateDistance(restaurantLat, restaurantLong, location!!.latitude, location!!.longitude)
                        Text(text = "A $metros metros", style= Typography.headlineSmall)
                    }
                    else{
                        Text(text = "Calculando cercanía", style= Typography.headlineSmall)
                    }
                }

            }
            LazyRow (
                modifier = Modifier
                    .padding(5.dp)
            )
            {
                items(dishList) {
                    CardProduct(
                        id = it.id,
                        name = it.name,
                        rating = it.rating,
                        restaurantName = "central",
                        price = it.price,
                        image = it.image,
                        context = context
                    )
                }
            }
            Row {
                Button(onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red))
                {
                    Text("Añadir Favorito")
                }
                Button(onClick = {
                                 openGoogleMapsWithGeopoint(restaurantLat, restaurantLong, context)
                },
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red))
                {
                    Text("¿Como llegar?")
                }
            }
        }

    }
}

fun openGoogleMapsWithGeopoint(latitude: Double, longitude: Double, context: Context) {
    val geoUri = "geo:$latitude,$longitude"
    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
    //mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
    /*
    if (mapIntent.resolveActivity(context.getPa) != null) {
        Toast.makeText(
            context,
            "Coordenadas: Long $longitude Lat $latitude",
            Toast.LENGTH_LONG
        ).show()
        context.startActivity(mapIntent)

    } else {
        Toast.makeText(
            context,
            "Google Maps no está instalado. Coordenadas: Long $longitude Lat $latitude",
            Toast.LENGTH_LONG
        ).show()
    }*/

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardProduct(name: String, id: Int, rating: Double, restaurantName: String, price: Double, image: String, context: Context ) {
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
        ),
        onClick = {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("productId", id.toString())
            context.startActivity(intent)
        }
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
fun FilledButtonExample(onClick: () -> Unit) {
    Button(onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Orange))
    {
        Text("Aplicar Filtros")
    }
}


fun calculateDistance(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double
): Int {
    val radiusOfEarth = 6371000.0 // Earth's radius in meters

    val lat1Rad = Math.toRadians(lat1)
    val lon1Rad = Math.toRadians(lon1)
    val lat2Rad = Math.toRadians(lat2)
    val lon2Rad = Math.toRadians(lon2)

    val dLat = lat2Rad - lat1Rad
    val dLon = lon2Rad - lon1Rad

    val a = sin(dLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return (radiusOfEarth * c).roundToInt()
}



