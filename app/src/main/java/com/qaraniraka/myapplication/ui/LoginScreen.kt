package com.qaraniraka.myapplication.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qaraniraka.myapplication.MainActivity
import com.qaraniraka.myapplication.R
import com.qaraniraka.myapplication.model.UserLoginPostData
import com.qaraniraka.myapplication.viewmodel.UserSessionViewModel
import com.qaraniraka.myapplication.viewmodel.UserUiState
import com.qaraniraka.myapplication.viewmodel.UserViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen() {
    val context = LocalContext.current

    val userViewModelForLogin: UserViewModel = viewModel()
    val userSessionViewModel: UserSessionViewModel =
        viewModel(factory = UserSessionViewModel.Factory)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    var haveTouchedEmail by remember { mutableStateOf(false) }
    var haveTouchedPassword by remember { mutableStateOf(false) }

    var isEmailValid = email.isNotEmpty()
    var isPasswordValid = password.isNotEmpty()
    var isFormValid = isEmailValid and isPasswordValid

    if (userViewModelForLogin.userUiState is UserUiState.LoginSuccess) {
        userSessionViewModel.saveUserSession(
            (userViewModelForLogin.userUiState as UserUiState.LoginSuccess).data.session
        )
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(64.dp)
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .align(Alignment.Start)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                ),
            ) {
                OutlinedTextField(
                    label = { Text("Email") },
                    value = email,
                    onValueChange = {
                        haveTouchedEmail = true
                        email = it
                    },
                    isError = haveTouchedEmail and !isEmailValid,
                    supportingText = {
                        if (haveTouchedEmail and !isEmailValid) {
                            Text("Tolong isi email")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    label = { Text("Password") },
                    value = password,
                    onValueChange = {
                        haveTouchedPassword = true
                        password = it
                    },
                    isError = haveTouchedPassword and !isPasswordValid,
                    supportingText = {
                        if (haveTouchedPassword and !isPasswordValid) {
                            Text("Tolong isi password")
                        }
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                painter = painterResource(id = if (showPassword) R.drawable.visibility_off_24px else R.drawable.visibility_24px),
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = {
                    userViewModelForLogin.loginUser(UserLoginPostData(email, password))
                },
                enabled = isFormValid,
                modifier = Modifier
                    .width(120.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.End)
            ) {
                Text(text = "Login")
            }
            if (userViewModelForLogin.userUiState is UserUiState.Error) {
                Text(
                    text = "Email atau password salah",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(top = 32.dp)
                )
            }
        }
    }
}