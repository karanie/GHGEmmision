package com.qaraniraka.myapplication.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qaraniraka.myapplication.MainActivity
import com.qaraniraka.myapplication.R
import com.qaraniraka.myapplication.model.UserLoginPostData
import com.qaraniraka.myapplication.model.UserRegistrationPostData
import com.qaraniraka.myapplication.viewmodel.UserSessionViewModel
import com.qaraniraka.myapplication.viewmodel.UserUiState
import com.qaraniraka.myapplication.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreen() {
    val context = LocalContext.current

    val userViewModelForLogin: UserViewModel = viewModel()
    val userViewModelForRegister: UserViewModel = viewModel()
    val userViewModelForEmailChecking: UserViewModel = viewModel()
    val userSessionViewModel: UserSessionViewModel = viewModel(factory = UserSessionViewModel.Factory)

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val birthdayDatePickerState = rememberDatePickerState()
    birthdayDatePickerState.displayMode = DisplayMode.Input
    var birthdayDatePickerOpenDialog by remember { mutableStateOf(false) }
    val formattedDisplayedBirthDay =
        if (birthdayDatePickerState.selectedDateMillis != null) java.text.SimpleDateFormat("dd-MM-yyyy")
            .format(birthdayDatePickerState.selectedDateMillis) else ""
    val formattedBirthDay =
        if (birthdayDatePickerState.selectedDateMillis != null) java.text.SimpleDateFormat("yyyy-MM-dd")
            .format(birthdayDatePickerState.selectedDateMillis) else ""

    var haveTouchedFullName by remember { mutableStateOf(false) }
    var haveTouchedEmail by remember { mutableStateOf(false) }
    var haveTouchedPassword by remember { mutableStateOf(false) }

    val isFullNameValid = fullName.isNotEmpty()
    val isEmailValid =
        email.isNotEmpty() and Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+\$").matches(email)
    val isEmailNotAvailable =
        userViewModelForEmailChecking.userUiState == UserUiState.EmailAvaiable(false)
    val isPasswordValid = password.isNotEmpty() and (password.length >= 8)
    val isBirthdayValid = birthdayDatePickerState.selectedDateMillis != null
    val isFormValid = (isFullNameValid
            and isEmailValid
            and !isEmailNotAvailable
            and isPasswordValid
            and isBirthdayValid)

    if (userViewModelForRegister.userUiState is UserUiState.RegisterSuccess) {
        userViewModelForLogin.loginUser(UserLoginPostData(email, password))
    }

    if (userViewModelForLogin.userUiState is UserUiState.LoginSuccess) {
        userSessionViewModel.saveUserSession((userViewModelForLogin.userUiState as UserUiState.LoginSuccess).data.session)
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
                text = "Register",
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
                    value = fullName,
                    label = { Text("Nama Lengkap") },
                    onValueChange = {
                        haveTouchedFullName = true
                        fullName = it
                    },
                    supportingText = {
                        if (haveTouchedFullName and !isFullNameValid) {
                            Text("Tolong isi nama lengkap")
                        }
                    },
                    isError = haveTouchedFullName and !isFullNameValid,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    label = { Text("Email") },
                    onValueChange = {
                        haveTouchedEmail = true
                        email = it
                        userViewModelForEmailChecking.isEmailAvailable(email)
                    },
                    supportingText = {
                        if (haveTouchedEmail and !isEmailValid) {
                            Text("Tolong isi email dengan benar")
                        } else if (haveTouchedEmail and isEmailNotAvailable) {
                            Text("Email sudah diregister")
                        }
                    },
                    trailingIcon = {
                           if (haveTouchedEmail and !isEmailNotAvailable and isEmailValid) {
                                Icon(painter = painterResource(id = R.drawable.ic_check), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                           } else if (haveTouchedEmail and (isEmailNotAvailable or !isEmailValid)) {
                               Icon(painter = painterResource(id = R.drawable.ic_close), contentDescription = null, tint = MaterialTheme.colorScheme.error)
                           }
                    },
                    isError = haveTouchedEmail and (!isEmailValid or isEmailNotAvailable),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
                Box {
                    OutlinedTextField(
                        value = formattedDisplayedBirthDay,
                        onValueChange = {},
                        label = { Text("Tanggal Lahir") },
                        readOnly = true,
                        supportingText = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) { birthdayDatePickerOpenDialog = true }
                    )
                }
                if (birthdayDatePickerOpenDialog) {
                    DatePickerDialog(
                        onDismissRequest = { birthdayDatePickerOpenDialog = false },
                        confirmButton = {
                            TextButton(onClick = { birthdayDatePickerOpenDialog = false }) {
                                Text("Atur")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { birthdayDatePickerOpenDialog = false }) {
                                Text("Tutup")
                            }
                        }
                    ) {
                        DatePicker(state = birthdayDatePickerState)
                    }
                }
                OutlinedTextField(
                    value = password,
                    label = { Text("Password") },
                    onValueChange = {
                        haveTouchedPassword = true
                        password = it
                    },
                    supportingText = {
                        if (haveTouchedPassword and !isPasswordValid) {
                            Text("Panjang password harus lebih dari 8 karakter")
                        }
                    },
                    isError = haveTouchedPassword and !isPasswordValid,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                    userViewModelForRegister.registerUser(
                        UserRegistrationPostData(
                            email, password, fullName, formattedBirthDay
                        )
                    )
                },
                enabled = isFormValid,
                modifier = Modifier
                    .width(120.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.End)
            ) {
                Text(text = "Register")
            }
        }
    }
}