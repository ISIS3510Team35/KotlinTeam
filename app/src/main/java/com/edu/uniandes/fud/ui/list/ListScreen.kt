package com.edu.uniandes.fud.ui.list

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.edu.uniandes.fud.ui.theme.Manrope
import com.edu.uniandes.fud.ui.theme.OrangeSoft
import com.edu.uniandes.fud.ui.theme.Typography
import com.edu.uniandes.fud.viewModel.list.ListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(viewModel: ListViewModel) {
    Scaffold(
        containerColor = Color.White,
        topBar = { CustomTopBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
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
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "dashboard_search"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = OrangeSoft),
        title = {
            Text(text = "Almuerzo")
        }
    )
}


@Preview
@Composable
fun ElementGrid() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardProduct(name: String, restaurantName: String, price: Double, image: String, id: Int, viewModel: ListViewModel) {
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