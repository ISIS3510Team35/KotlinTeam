package com.edu.uniandes.fud.ui.register

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.edu.uniandes.fud.HomeActivity
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.ui.theme.backgroundSecondary
import com.edu.uniandes.fud.ui.theme.buttonBackground
import com.edu.uniandes.fud.ui.theme.buttonText
import com.edu.uniandes.fud.ui.theme.textField
import com.edu.uniandes.fud.ui.theme.textFieldBackground
import com.edu.uniandes.fud.viewModel.register.RegisterViewModel


@Composable
fun RegisterScreen(viewModel: RegisterViewModel) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(backgroundSecondary)
			.padding(horizontal = 50.dp)
	) {
		Register(Modifier.align(Alignment.Center), viewModel)
	}
	
}

@Composable
fun Register(align: Modifier, viewModel: RegisterViewModel) {
	
	val email: String by viewModel.email.observeAsState(initial = "")
	val password: String by viewModel.password.observeAsState(initial = "")
	val registerEnable: Boolean by viewModel.registerEnable.observeAsState(initial = false)
	
	
	Column(
		modifier = Modifier.fillMaxHeight(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		HeaderImage(Modifier.align(Alignment.CenterHorizontally))
		Spacer(modifier = Modifier.padding(16.dp))
		EmailField(email) { viewModel.onRegisterChanged(it, password) }
		Spacer(modifier = Modifier.padding(16.dp))
		Passwordfield(password) { viewModel.onRegisterChanged(email, it) }
		Spacer(modifier = Modifier.padding(32.dp))
		RegisterButton(registerEnable) { viewModel.onRegisterSelected() }
	}
}


@Composable
fun HeaderImage(modifier: Modifier) {
	Image(
		painter = painterResource(id = R.drawable.logo),
		contentDescription = "Logo",
		modifier = modifier.fillMaxWidth().height(75.dp)
	)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(email: String, onTextFieldChanged:(String) -> Unit) {
	
	TextField(
		value = email,
		onValueChange = { onTextFieldChanged(it) },
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
fun Passwordfield(password: String, onTextFieldChanged:(String) -> Unit) {
	TextField(
		value = password,
		onValueChange = { onTextFieldChanged(it) },
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
fun RegisterButton(registerEnable: Boolean, onRegisterSelected: () -> Unit) {
	val context = LocalContext.current
	Button(
		onClick = {
			onRegisterSelected()
			context.startActivity(Intent(context, HomeActivity::class.java))
				  },
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 10.dp, horizontal = 50.dp),
		colors = ButtonDefaults.buttonColors(
			containerColor = buttonBackground,
			contentColor = buttonText
		), enabled = registerEnable
	) {
		Text(text = "REGISTER")
	}
}
