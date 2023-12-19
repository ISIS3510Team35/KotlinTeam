package com.edu.uniandes.fud.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.edu.uniandes.fud.ProductActivity
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.domain.ProductRestaurant
import com.edu.uniandes.fud.ui.theme.BarXD
import com.edu.uniandes.fud.ui.theme.Manrope
import com.edu.uniandes.fud.ui.theme.OrangeSoft
import com.edu.uniandes.fud.ui.theme.Typography
import com.edu.uniandes.fud.viewModel.list.ListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RequiresApi(api = Build.VERSION_CODES.O)
fun ListScreen(viewModel: ListViewModel) {

    val productsRestaurant: List<ProductRestaurant> by viewModel.products.observeAsState(initial = emptyList())

    Scaffold(
        containerColor = Color.White,
        topBar = { CustomTopBar(viewModel) }
    ) { innerPadding ->
        BarXD()
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        top = 16.dp,
                        end = 12.dp,
                        bottom = 16.dp
                    ),
                    content = {
                        items(productsRestaurant.size) {index->
                            CardProduct(productsRestaurant[index].name,
                                productsRestaurant[index].restaurant.name,
                                productsRestaurant[index].price,
                                productsRestaurant[index].image,
                                productsRestaurant[index].id,
                                viewModel)
                        }
                    }
                )
            }
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(api = Build.VERSION_CODES.O)
@Composable
fun CustomTopBar(viewModel: ListViewModel) {
    val context = LocalContext.current
    val titulo: String by viewModel.titulo.observeAsState(initial = "")

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = OrangeSoft),
        navigationIcon = {
            IconButton(
                modifier = Modifier.fillMaxHeight(),
                onClick = {
                    val activity: Activity = context as Activity
                    activity.finish()
                }
            ) {
                Image(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "dashboard_search"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = OrangeSoft)
            .zIndex(5f),
        title = {
            Text(text = titulo)
        }
    )
}


@Preview
@Composable
fun ElementGrid() {

}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(api = Build.VERSION_CODES.O)
@Composable
fun CardProduct(name: String, restaurantName: String, price: Double, image: String, id: Int, viewModel: ListViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .fillMaxWidth()
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