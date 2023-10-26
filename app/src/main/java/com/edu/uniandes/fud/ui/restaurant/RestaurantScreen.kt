package com.edu.uniandes.fud.ui.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.ui.theme.Manrope
import com.edu.uniandes.fud.ui.theme.OrangeSoft
import com.edu.uniandes.fud.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RestaurantScreen() {
    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .background(color = Color.White),
        topBar = { CustomTopBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                RestaurantImgName(
                    restaurant = "Cafetería Central Uniandes",
                    restImg = R.drawable.cafeteria_central
                )
            }
            item {
                CarousselCardDish()
            }
            item {
                CarousselRestaurants()
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
fun RestaurantImgName(restaurant: String, restImg: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(restImg),
            contentDescription = null,
            alpha = 0.9f,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .height(200.dp)
        )

        Text(
            text = restaurant,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            style = TextStyle(
                color = Color.White,
                fontFamily = Manrope,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                lineHeight = 28.sp,
                letterSpacing = (-0.5).sp
            )
        )

    }
}

@Composable
fun CardDish(name: String, price: String, imgId: Int) {
    Box(
        modifier = Modifier.width(220.dp),
        contentAlignment = Alignment.TopCenter
    ) {
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
            )
        ) {
            Spacer(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
            Text(
                text = name,
                style = Typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = price,
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
            painter = painterResource(imgId),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
fun CarousselCardDish() {
    Text(
        text = "Recomendados para ti",
        style = Typography.titleMedium,
        modifier = Modifier.padding(horizontal = 17.dp)
    )
    LazyRow {
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
        item {
            CardDish(
                name = "Almuerzo",
                price = "13.9K",
                imgId = R.drawable.almuerzo_dia
            )
        }
        item {
            CardDish(
                name = "Espagueti",
                price = "18.9K",
                imgId = R.drawable.pasta_kt
            )
        }
        item {
            CardDish(
                name = "Sandwich",
                price = "15.9K",
                imgId = R.drawable.subway_sandwich
            )
        }
    }
}


@Composable
fun CardOthers(name: String, picture: Int, price: String) {
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
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .shadow(
                    elevation = 5.dp,
                ),
            painter = painterResource(picture),
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
fun CarousselRestaurants() {
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
        item {
            CardOthers("Hamburguesa de lenteja", R.drawable.hamb_lent, "13.9K")
        }
        item {
            CardOthers("Arroz picante mexicano", R.drawable.arroz_mex, "15.9K")
        }
        item {
            CardOthers("Berenjenas tempura", R.drawable.b_temp, "10.9K")
        }
        item {
            CardOthers("Bandeja paisa", R.drawable.band_paisa, "14.9K")
        }
    }
}