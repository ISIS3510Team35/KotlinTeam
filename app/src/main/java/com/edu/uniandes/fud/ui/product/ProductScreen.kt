package com.edu.uniandes.fud.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.domain.Restaurant
import com.edu.uniandes.fud.ui.theme.*
import com.edu.uniandes.fud.viewModel.product.ProductViewModel
import com.google.firebase.firestore.GeoPoint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(viewModel: ProductViewModel) {
    Scaffold(
        topBar = { CustomTopBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                ProductImg(viewModel)
            }
            item {
                ProductNameDesc(viewModel)
            }
            item {
                CarousselOthers(viewModel)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar() {
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
                        Image(
                            modifier = Modifier.fillMaxHeight(),
                            painter = painterResource(id = R.drawable.uniandes),
                            contentDescription = "dashboard_search"
                        )
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

@Composable
fun ProductImg(viewModel: ProductViewModel) {

    val product: ProductRestaurant by viewModel.product.observeAsState(
        initial = ProductRestaurant(
            id = 0,
            name = "",
            description = "",
            price = 0,
            offerPrice = 0,
            inOffer = false,
            rating = 0.0,
            type = "",
            category = "",
            image = "",
            restaurantId = 0,
            Restaurant(id = 0, name = "", location = GeoPoint(0.0,0.0), image = "")
        )
    )

    AsyncImage(
        model = product.image,
        placeholder = painterResource(R.drawable.loading),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(200.dp)
    )

}

@Composable
fun ProductNameDesc(viewModel: ProductViewModel) {

    val product: ProductRestaurant by viewModel.product.observeAsState(
        initial = ProductRestaurant(
            id = 0,
            name = "",
            description = "",
            price = 0,
            offerPrice = 0,
            inOffer = false,
            rating = 0.0,
            type = "",
            category = "",
            image = "",
            restaurantId = 0,
            Restaurant(id = 0, name = "", location = GeoPoint(0.0,0.0), image = "")
        )
    )

    Text(
        text = product.name,
        style = Typography.titleLarge,
        modifier = Modifier.padding(horizontal = 25.dp)
    )
    Text(
        text = product.restaurant.name,
        style = Typography.headlineLarge,
        modifier = Modifier.padding(horizontal = 25.dp)
    )
    Text(
        text = product.price.toString(),
        style = TextStyle(
            color = Orange,
            fontFamily = Manrope,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.5).sp
        ),
        modifier = Modifier.padding(horizontal = 25.dp)
    )
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
    ) {

        Text(
            text = product.rating.toString(),
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(top = 10.dp)
            .padding(horizontal = 25.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = product.name,
            style = Typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun CardOthers(name: String, picture: String, price: String) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(200.dp)
            .padding(10.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        AsyncImage(
            model = picture,
            modifier = Modifier
                .fillMaxSize(),
            placeholder = painterResource(R.drawable.loading),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = name,
                style = Typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = price,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                textAlign = TextAlign.Center,
                style = Typography.labelMedium
            )
        }
    }
}


@Composable
fun CarousselOthers(viewModel: ProductViewModel) {

    val otherProducts : List<ProductRestaurant> by viewModel.top3Products.observeAsState(initial = emptyList())

    Text(
        text = "Otros platos",
        style = Typography.titleMedium,
        modifier = Modifier
            .padding(top = 5.dp)
            .padding(horizontal = 17.dp)
    )

    LazyRow {
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
        items(otherProducts) {
            CardOthers(
                name = it.name,
                picture = it.image,
                price = it.price.toString())
        }
    }
}

