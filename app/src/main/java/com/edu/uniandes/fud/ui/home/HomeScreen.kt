package com.edu.uniandes.fud.ui.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.edu.uniandes.fud.ListActivity
import com.edu.uniandes.fud.LoginActivity
import com.edu.uniandes.fud.AccountActivity
import com.edu.uniandes.fud.ProductActivity
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.RestaurantActivity
import com.edu.uniandes.fud.SearchActivity
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.ui.theme.Gold
import com.edu.uniandes.fud.ui.theme.Manrope
import com.edu.uniandes.fud.ui.theme.OrangeSoft
import com.edu.uniandes.fud.ui.theme.Red
import com.edu.uniandes.fud.ui.theme.Typography
import com.edu.uniandes.fud.viewModel.home.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(api = Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val context = LocalContext.current

    Scaffold(
        containerColor = Color.White,
        topBar = { CustomTopBar(viewModel, context) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                SearchBar(viewModel, context)
            }
            item {
                ButtonHour(viewModel, context)
            }
            item {
                CarousselMealType(viewModel, context)
            }
            item {
                CarousselTop3Product(viewModel)
            }
            item {
                CarousselProductOffers(viewModel)
            }
            item {
                CarousselFavorites(viewModel)
            }
            item {
                CarousselRecommended(viewModel)
            }
        }

    }
}

@Composable
@RequiresApi(api = Build.VERSION_CODES.O)
fun ButtonHour(viewModel: HomeViewModel, context: Context){
    val textHourButton: String by viewModel.textButtonHour.observeAsState(initial = "")
    Button(
        onClick = {
            val intent = Intent(context, ListActivity::class.java)
            intent.putExtra("tipo", "hourProducts")
            context.startActivity(intent)
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 17.dp)
            .padding(bottom = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Red,
            contentColor = Color.White
        ),
    ) {
        Text(textHourButton,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                )
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
            contentDescription = "Go",
            modifier = Modifier.size(20.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RequiresApi(api = Build.VERSION_CODES.O)
fun CustomTopBar(viewModel: HomeViewModel, context: Context) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = OrangeSoft),
        navigationIcon = {
            IconButton(
                modifier = Modifier.fillMaxHeight(),
                onClick = {
                    val intent = Intent(context, AccountActivity::class.java)
                    intent.putExtra("userId", viewModel.userId.value.toString())
                    context.startActivity(intent)
                }
            ) {
                Image(
                    modifier = Modifier
                        .padding(5.dp),
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = "dashboard_search"
                )
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
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
                        val isLocationInRange = remember { mutableStateOf(false) }

                        viewModel.isLocationInRange.observeAsState().value?.let {
                            isLocationInRange.value = it
                        }

                        if (isLocationInRange.value) {
                            Image(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(start=1.dp),
                                painter = painterResource(id = R.drawable.uniandes),
                                contentDescription = "dashboard_search"
                            )

                        }
                    }
                }
            }
        },
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
@RequiresApi(api = Build.VERSION_CODES.O)
fun SearchBar(viewModel: HomeViewModel, context: Context) {


    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val iconRight: Int by viewModel.iconRight.observeAsState(initial = R.drawable.ic_sliders)
    val query: String by viewModel.query.observeAsState(initial = "")

    Row(
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
                intent.putExtra("query", query)
                context.startActivity(intent)
            }),
            value = query,
            placeholder = {
                Text(text = "Busca tu comida de hoy")
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
            onClick = {
                if (viewModel.isReadyToChange()) {
                    val intent = Intent(context, SearchActivity::class.java)
                    intent.putExtra("query", viewModel.getQuery())
                    context.startActivity(intent)
                } else {
                    Toast.makeText(
                        context,
                        "Realice una búsqueda primero antes de filtrar el contenido",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        ) {
            Icon(
                painterResource(id = iconRight),
                contentDescription = null
            )
        }
    }
}


// Diferentes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RequiresApi(api = Build.VERSION_CODES.O)
fun CardProduct(name: String, restaurantName: String, price: Double, image: String, id: Int, viewModel: HomeViewModel) {
    Box(
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
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            onClick = {
                viewModel.sendFavReport(context)
                val intent = Intent(context, ProductActivity::class.java)
                intent.putExtra("productId", id.toString())
                context.startActivity(intent)
            },
        ) {
            Spacer(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
            Text(
                text = AnnotatedString(name),
                style = TextStyle(
                    fontFamily = Manrope,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    letterSpacing = (-0.5).sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                // textAlign = TextAlign.Center
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
                text = price.toString() + "K",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RequiresApi(api = Build.VERSION_CODES.O)
fun CardProductOffer(
    name: String,
    id: Int,
    rating: Double,
    restaurantName: String,
    price: Double,
    offerPrice: Double,
    image: String,
    viewModel: HomeViewModel
) {
    val context = LocalContext.current
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
            viewModel.sendPromoReport(context)
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("productId", id.toString())
            context.startActivity(intent)
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .shadow(
                    elevation = 5.dp,
                )
        ) {
            AsyncImage(
                model = image,
                modifier = Modifier
                    .fillMaxSize(),
                placeholder = painterResource(R.drawable.loading),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = AnnotatedString(name),
                style = TextStyle(
                    fontFamily = Manrope,
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    letterSpacing = (-0.5).sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            )

            Text(
                text = price.toString() + "K",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                textAlign = TextAlign.Center,
                style = Typography.labelMedium
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

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
@RequiresApi(api = Build.VERSION_CODES.O)
fun CarousselTop3Product(viewModel: HomeViewModel) {

    val top3Products: List<ProductRestaurant> by viewModel.top3Products.observeAsState(initial = emptyList())

    Text(
        text = "Top 3 restaurantes un tu zona",
        style = Typography.titleMedium,
        modifier = Modifier.padding(horizontal = 17.dp)
    )
    LazyRow {
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }

        items(top3Products) {
            CardProduct(
                name = it.name,
                restaurantName = it.restaurant.name,
                price = it.price,
                image = it.image,
                id = it.id,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RequiresApi(api = Build.VERSION_CODES.O)
fun CardTypeMeal(title: String, image: String, id:Int, context: Context) {
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
        ),
        onClick = {
            val intent = Intent(context, RestaurantActivity::class.java)
            intent.putExtra("restaurantId", id.toString())
            context.startActivity(intent)
        },

    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 15.dp, end = 15.dp)
        )
        AsyncImage(
            model = image,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .weight(1f)
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(10.dp)
                ),
            placeholder = painterResource(R.drawable.loading),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@RequiresApi(api = Build.VERSION_CODES.O)
fun CarousselMealType(viewModel: HomeViewModel, context: Context) {

    val top3InteractedRestaurants: List<Restaurant> by viewModel.top3InteractedRestaurants.observeAsState(initial = emptyList())


    Text(
        text = "Restaurantes más visitados por ti",
        style = Typography.titleMedium,
        modifier = Modifier.padding(horizontal = 17.dp)
    )
    LazyRow {
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
        items(top3InteractedRestaurants) {
            CardTypeMeal(it.name, it.image, it.id, context)
        }
    }
}

@Composable
@RequiresApi(api = Build.VERSION_CODES.O)
fun CarousselProductOffers(viewModel: HomeViewModel) {

    val offerProducts: List<ProductRestaurant> by viewModel.offerProducts.observeAsState(initial = emptyList())


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
                image = it.image,
                viewModel = viewModel
            )

        }


    }
}

@Composable
@RequiresApi(api = Build.VERSION_CODES.O)
fun CarousselFavorites(viewModel: HomeViewModel) {

    val favoriteList: List<ProductRestaurant> by viewModel.favoriteDishes.observeAsState(initial = emptyList())

    Text(
        text = "Tus favoritos",
        style = Typography.titleMedium,
        modifier = Modifier.padding(horizontal = 17.dp)
    )
    LazyRow {
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }

        items(favoriteList) {
            CardProduct(
                name = it.name,
                restaurantName = it.restaurant.name,
                price = it.price,
                image = it.image,
                id = it.id,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun CarousselRecommended(viewModel: HomeViewModel) {

    val favoriteList: List<ProductRestaurant> by viewModel.recommendedDishes.observeAsState(initial = emptyList())

    Text(
        text = "Recomendados para ti",
        style = Typography.titleMedium,
        modifier = Modifier.padding(horizontal = 17.dp)
    )
    LazyRow {
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }

        items(favoriteList) {
            CardProduct(
                name = it.name,
                restaurantName = it.restaurant.name,
                price = it.price,
                image = it.image,
                id = it.id,
                viewModel = viewModel
            )
        }
    }
}