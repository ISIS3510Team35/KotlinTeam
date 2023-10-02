package com.edu.uniandes.gastronomicoffer.ui.login

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.edu.uniandes.gastronomicoffer.R
import com.edu.uniandes.gastronomicoffer.ui.theme.backgroundSecondary
import com.edu.uniandes.gastronomicoffer.ui.theme.buttonBackground
import com.edu.uniandes.gastronomicoffer.ui.theme.buttonText
import com.edu.uniandes.gastronomicoffer.ui.theme.textField
import com.edu.uniandes.gastronomicoffer.ui.theme.textFieldBackground

@Composable
fun LoginScreen() {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(backgroundSecondary)
			.padding(horizontal = 50.dp)
	) {
		Login(Modifier.align(Alignment.Center))
	}
	
}

@Composable
fun Login(modifier: Modifier) {
	Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
		HeaderImage(Modifier.align(Alignment.CenterHorizontally))
		Spacer(modifier = Modifier.padding(16.dp))
		UserField()
		Spacer(modifier = Modifier.padding(16.dp))
		Passwordfield()
		Spacer(modifier = Modifier.padding(32.dp))
		LoginButton()
	}
}


@Composable
fun HeaderImage(modifier: Modifier) {
	Image(
		painter = painterResource(id = R.drawable.logo),
		contentDescription = "Logo",
		modifier = modifier
	)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserField() {
	
	val textValue = remember {
		mutableStateOf("")
	}
	
	TextField(
		value = "",
		onValueChange = {},
		modifier = Modifier
			.fillMaxWidth(),
		placeholder = { Text(text = "Email") },
		keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
		singleLine = true,
		maxLines = 1,
		colors = TextFieldDefaults.textFieldColors(
			textColor = textField,
			containerColor = textFieldBackground,
			focusedIndicatorColor = Color.Transparent,
			unfocusedIndicatorColor = Color.Transparent
		)
	)
	
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Passwordfield() {
	TextField(
		value = "",
		onValueChange = {},
		modifier = Modifier
			.fillMaxWidth(),
		placeholder = { Text(text = "Password") },
		keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
		singleLine = true,
		maxLines = 1,
		colors = TextFieldDefaults.textFieldColors(
			textColor = textField,
			containerColor = textFieldBackground,
			focusedIndicatorColor = Color.Transparent,
			unfocusedIndicatorColor = Color.Transparent
		)
	)
}


@Composable
fun LoginButton() {
	Button(
		onClick = {},
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 10.dp, horizontal = 50.dp),
		colors = ButtonDefaults.buttonColors(
			containerColor = buttonBackground,
			contentColor = buttonText
		)
	) {
		Text(text = "LOG IN")
	}
}
