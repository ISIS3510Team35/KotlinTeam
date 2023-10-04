package com.edu.uniandes.fud.ui.dish

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RestaurantScreen() {
    Scaffold(
        topBar = { CustomTopBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                DishImg(restImg = R.drawable.almuerzo_dia)
            }
            item {
                DishNameDesc(
                    "Almuerzo del día",
                    "Cafetería Central Uniandes",
                    4.3f,
                    "13.9K",
                    "Una entrada común en Colombia es la sopa. El \"ajiaco\" es una sopa espesa y reconfortante que se prepara con papas, pollo, maíz, alcaparras y crema de leche. Otra opción es la \"sopa de lentejas\" o la \"sopa de aguacate\", que son igualmente deliciosas. \n\nUno de los platos más emblemáticos de Colombia es la \"bandeja paisa\". Este plato abundante incluye arroz, frijoles, carne molida, chicharrón, chorizo, huevo frito, aguacate y plátano maduro frito. Otra opción popular es el \"ajiaco santafereño\", un guiso de pollo con papas y maíz que se sirve con arroz."
                )
            }
            item {
                CarousselOthers()
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
fun DishImg(restImg: Int) {
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

}

@Composable
fun DishNameDesc(dishName: String, dishRest: String, rating: Float, price: String, dishDesc: String) {
    Text(
        text = dishName,
        style = Typography.titleLarge,
        modifier = Modifier.padding(horizontal = 25.dp)
    )
    Text(
        text = dishRest,
        style = Typography.headlineLarge,
        modifier = Modifier.padding(horizontal = 25.dp)
    )
    Text(
        text = price,
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
            text = dishDesc,
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
fun CarousselOthers() {
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

