package com.edu.uniandes.fud.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.domain.User
import com.edu.uniandes.fud.ui.theme.Manrope
import com.edu.uniandes.fud.ui.theme.OrangeSoft
import com.edu.uniandes.fud.ui.theme.Typography
import com.edu.uniandes.fud.ui.theme.backgroundSecondary
import com.edu.uniandes.fud.viewModel.account.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(viewModel: AccountViewModel) {
    Scaffold(
        containerColor = backgroundSecondary,
        modifier = Modifier
            .background(color = backgroundSecondary),
        topBar = { CustomTopBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .background(backgroundSecondary)
        ) {
            item {
                ImgName(viewModel)
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            item {
                NameField(viewModel)
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            item {
                NumberField(viewModel)
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = OrangeSoft),
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
fun ImgName(viewModel: AccountViewModel) {

    val user: User by viewModel.user.observeAsState(
        initial = User(
            id = 0,
            username = "",
            name = "",
            number = "",
            password = "",
            documentId = ""
        )
    )

    Column() {
        Image(
            painter = painterResource(R.drawable.person),
            contentDescription = null,
            modifier = Modifier.size(50.dp, 50.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = user.name,
            style = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                letterSpacing = (-0.2).sp,
                textAlign = TextAlign.Start,
            )
        )
    }
}

@Composable
fun NameField(viewModel: AccountViewModel) {

    val user: User by viewModel.user.observeAsState(
        initial = User(
            id = 0,
            username = "",
            name = "",
            number = "",
            password = "",
            documentId = ""
        )
    )

    Column() {
        Text(
            "USER",
            style = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                letterSpacing = (-0.2).sp,
                textAlign = TextAlign.Start,
            )
        )

        Card(
            modifier = Modifier
                .width(250.dp)
                .padding(top = 10.dp)
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
                text = user.username,
                style = Typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun NumberField(viewModel: AccountViewModel) {

    val user: User by viewModel.user.observeAsState(
        initial = User(
            id = 0,
            username = "",
            name = "",
            number = "",
            password = "",
            documentId = ""
        )
    )

    Column() {
        Text(
            "NUMBER",
            style = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                letterSpacing = (-0.2).sp,
                textAlign = TextAlign.Start,
            )
        )

        Card(
            modifier = Modifier
                .width(250.dp)
                .padding(top = 10.dp)
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
                text = user.number,
                style = Typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}