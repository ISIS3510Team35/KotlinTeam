package com.edu.uniandes.fud.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edu.uniandes.fud.R
import com.edu.uniandes.fud.ui.theme.Manrope
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
	val name: String by viewModel.name.observeAsState(initial = "")
	val number: String by viewModel.number.observeAsState(initial = "")
	val password: String by viewModel.password.observeAsState(initial = "")
	val passwordConfirm: String by viewModel.passwordConfirm.observeAsState(initial = "")
	val registerEnable: Boolean by viewModel.registerEnable.observeAsState(initial = false)
	
	
	Column(
		modifier = Modifier.fillMaxHeight(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		HeaderImage(Modifier.align(Alignment.CenterHorizontally))
		Spacer(modifier = Modifier.padding(16.dp))
		EmailField(email) { viewModel.onRegisterChanged(it, name, number, password, passwordConfirm) }
		Spacer(modifier = Modifier.padding(16.dp))
		NameField(name) { viewModel.onRegisterChanged(email, it, number, password, passwordConfirm) }
		Spacer(modifier = Modifier.padding(16.dp))
		NumberField(number) { viewModel.onRegisterChanged(email, name, it, password, passwordConfirm) }
		Spacer(modifier = Modifier.padding(16.dp))
		PasswordField(password) { viewModel.onRegisterChanged(email, name, number, it, passwordConfirm) }
		Spacer(modifier = Modifier.padding(16.dp))
		PasswordConfirmField(passwordConfirm) { viewModel.onRegisterChanged(email, name, number, password, it) }
		Spacer(modifier = Modifier.padding(32.dp))
		RegisterButton { viewModel.onRegisterSelected() }
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
	
	Column(
		modifier = Modifier.fillMaxWidth()
	) {
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
		
		TextField(
			value = email,
			onValueChange = { onTextFieldChanged(it) },
			modifier = Modifier
				.fillMaxWidth()
				.height(64.dp)
				.padding(start = 4.dp, end = 4.dp, top = 4.dp)
				.clip(RoundedCornerShape(8.dp)),
			placeholder = { Text(text = "Type your username") },
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
			singleLine = true,
			maxLines = 1,
			colors = TextFieldDefaults.textFieldColors(
				textColor = textField,
				containerColor = textFieldBackground,
				focusedIndicatorColor = Color.Transparent,
				unfocusedIndicatorColor = Color.Transparent
			),
			leadingIcon = {
				Icon(
					painter = painterResource(id = R.drawable.person),
					contentDescription = null,
					modifier = Modifier
						.fillMaxHeight()
						.width(20.dp),
					Color(red = 0, green = 0, blue = 0, alpha = 255)
				)
			}
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameField(name: String, onTextFieldChanged:(String) -> Unit) {
	
	Column(
		modifier = Modifier.fillMaxWidth()
	) {
		Text(
			"NAME",
			style = TextStyle(
				fontFamily = Manrope,
				fontWeight = FontWeight.Bold,
				fontSize = 14.sp,
				letterSpacing = (-0.2).sp,
				textAlign = TextAlign.Start,
			)
		)
		
		TextField(
			value = name,
			onValueChange = { onTextFieldChanged(it) },
			modifier = Modifier
				.fillMaxWidth()
				.height(64.dp)
				.padding(start = 4.dp, end = 4.dp, top = 4.dp)
				.clip(RoundedCornerShape(8.dp)),
			placeholder = { Text(text = "Type your name") },
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
			singleLine = true,
			maxLines = 1,
			colors = TextFieldDefaults.textFieldColors(
				textColor = textField,
				containerColor = textFieldBackground,
				focusedIndicatorColor = Color.Transparent,
				unfocusedIndicatorColor = Color.Transparent
			),
			leadingIcon = {
				Icon(
					painter = painterResource(id = R.drawable.person),
					contentDescription = null,
					modifier = Modifier
						.fillMaxHeight()
						.width(20.dp),
					Color(red = 0, green = 0, blue = 0, alpha = 255)
				)
			}
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberField(number: String, onTextFieldChanged:(String) -> Unit) {
	
	Column(
		modifier = Modifier.fillMaxWidth()
	) {
		Text(
			"PHONE NUMBER",
			style = TextStyle(
				fontFamily = Manrope,
				fontWeight = FontWeight.Bold,
				fontSize = 14.sp,
				letterSpacing = (-0.2).sp,
				textAlign = TextAlign.Start,
			)
		)
		
		TextField(
			value = number,
			onValueChange = { onTextFieldChanged(it) },
			modifier = Modifier
				.fillMaxWidth()
				.height(64.dp)
				.padding(start = 4.dp, end = 4.dp, top = 4.dp)
				.clip(RoundedCornerShape(8.dp)),
			placeholder = { Text(text = "Type your number") },
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
			singleLine = true,
			maxLines = 1,
			colors = TextFieldDefaults.textFieldColors(
				textColor = textField,
				containerColor = textFieldBackground,
				focusedIndicatorColor = Color.Transparent,
				unfocusedIndicatorColor = Color.Transparent
			),
			leadingIcon = {
				Icon(
					painter = painterResource(id = R.drawable.person),
					contentDescription = null,
					modifier = Modifier
						.fillMaxHeight()
						.width(20.dp),
					Color(red = 0, green = 0, blue = 0, alpha = 255)
				)
			}
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(password: String, onTextFieldChanged:(String) -> Unit) {
	
	Column(
		modifier = Modifier.fillMaxWidth()
	) {
		Text(
			"PASSWORD",
			style = TextStyle(
				fontFamily = Manrope,
				fontWeight = FontWeight.Bold,
				fontSize = 14.sp,
				letterSpacing = (-0.2).sp,
				textAlign = TextAlign.Start,
			)
		)
		
		TextField(
			value = password,
			onValueChange = { onTextFieldChanged(it) },
			modifier = Modifier
				.fillMaxWidth()
				.height(64.dp)
				.padding(start = 4.dp, end = 4.dp, top = 4.dp)
				.clip(RoundedCornerShape(8.dp)),
			placeholder = { Text(text = "Type your password") },
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
			singleLine = true,
			maxLines = 1,
			colors = TextFieldDefaults.textFieldColors(
				textColor = textField,
				containerColor = textFieldBackground,
				focusedIndicatorColor = Color.Transparent,
				unfocusedIndicatorColor = Color.Transparent
			),
			visualTransformation = PasswordVisualTransformation(),
			leadingIcon = {
				Icon(
					painter = painterResource(id = R.drawable.lock),
					contentDescription = null,
					modifier = Modifier
						.fillMaxHeight()
						.width(20.dp),
					Color(red = 0, green = 0, blue = 0, alpha = 255)
				)
			}
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordConfirmField(password: String, onTextFieldChanged:(String) -> Unit) {
	
	Column(
		modifier = Modifier.fillMaxWidth()
	) {
		Text(
			"CONFIRM PASSWORD",
			style = TextStyle(
				fontFamily = Manrope,
				fontWeight = FontWeight.Bold,
				fontSize = 14.sp,
				letterSpacing = (-0.2).sp,
				textAlign = TextAlign.Start,
			)
		)
		
		TextField(
			value = password,
			onValueChange = { onTextFieldChanged(it) },
			modifier = Modifier
				.fillMaxWidth()
				.height(64.dp)
				.padding(start = 4.dp, end = 4.dp, top = 4.dp)
				.clip(RoundedCornerShape(8.dp)),
			placeholder = { Text(text = "Confirm your password") },
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
			singleLine = true,
			maxLines = 1,
			colors = TextFieldDefaults.textFieldColors(
				textColor = textField,
				containerColor = textFieldBackground,
				focusedIndicatorColor = Color.Transparent,
				unfocusedIndicatorColor = Color.Transparent
			),
			visualTransformation = PasswordVisualTransformation(),
			leadingIcon = {
				Icon(
					painter = painterResource(id = R.drawable.lock),
					contentDescription = null,
					modifier = Modifier
						.fillMaxHeight()
						.width(20.dp),
					Color(red = 0, green = 0, blue = 0, alpha = 255)
				)
			}
		)
	}
}


@Composable
fun RegisterButton(onRegisterSelected: () -> Unit) {
	Button(
		onClick = {
			onRegisterSelected()
		},
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 10.dp, horizontal = 50.dp),
		colors = ButtonDefaults.buttonColors(
			containerColor = buttonBackground,
			contentColor = buttonText
		)
	) {
		Text(text = "REGISTER")
	}
}
