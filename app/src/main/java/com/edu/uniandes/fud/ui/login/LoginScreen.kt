package com.edu.uniandes.fud.ui.login

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
import com.edu.uniandes.fud.viewModel.login.LoginViewModel


@Composable
fun LoginScreen(viewModel: LoginViewModel) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(backgroundSecondary)
			.padding(horizontal = 50.dp)
	) {
		Login(Modifier.align(Alignment.Center), viewModel)
	}
	
}

@Composable
fun Login(align: Modifier, viewModel: LoginViewModel) {
	
	val email: String by viewModel.email.observeAsState(initial = "")
	val password: String by viewModel.password.observeAsState(initial = "")
	val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
	
	
	Column(
		modifier = Modifier.fillMaxHeight(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		HeaderImage(Modifier.align(Alignment.CenterHorizontally))
		Spacer(modifier = Modifier.padding(48.dp))
		EmailField(email) { viewModel.onLoginChanged(it, password) }
		Spacer(modifier = Modifier.padding(16.dp))
		Passwordfield(password) { viewModel.onLoginChanged(email, it) }
		ForgotPasswordButton { viewModel.onForgotPWSelected() }
		Spacer(modifier = Modifier.padding(24.dp))
		LoginButton(loginEnable) { viewModel.onLoginSelected() }
		Spacer(modifier = Modifier.padding(24.dp))
		RegisterButton { viewModel.onRegisterSelected() }
	}
}


@Composable
fun HeaderImage(modifier: Modifier) {
	Image(
		painter = painterResource(id = R.drawable.logo),
		contentDescription = "Logo",
		modifier = modifier
			.fillMaxWidth()
			.height(75.dp)
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
fun Passwordfield(password: String, onTextFieldChanged:(String) -> Unit) {

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


@Composable
fun ForgotPasswordButton(onForgotPWSelected: () -> Unit) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.End
	) {
		TextButton(
			onClick = {
				onForgotPWSelected()
			},
			colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
		) {
			Text(
				text = "Forgot Password?",
				style = TextStyle(
					fontFamily = Manrope,
					fontSize = 14.sp,
					letterSpacing = (-0.2).sp,
					textAlign = TextAlign.End,
					color = Color.Gray
				)
			)
		}
	}
}







@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
	Button(
		onClick = {
			onLoginSelected()
				  },
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


@Composable
fun RegisterButton(onRegisterSelected: () -> Unit) {
    TextButton(
        onClick = {
			onRegisterSelected()
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
    ) {
        Text(
            text = "Don't have an account? ",
            style = TextStyle(
                fontFamily = Manrope,
                fontSize = 14.sp,
                letterSpacing = (-0.2).sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        )
        Text(
            text = "REGISTER",
            style = TextStyle(
                fontFamily = Manrope,
				fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                letterSpacing = (-0.2).sp,
                textAlign = TextAlign.Center,
                color = Color(255, 165, 0) // Orange color
            )
        )
    }
}
