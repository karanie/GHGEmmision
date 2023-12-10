package com.qaraniraka.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qaraniraka.myapplication.R

@Composable
fun WelcomeScreen(
    onRegisterButtonClicked: () -> Unit,
    onLoginButtonClicked: () -> Unit
) {
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Selamat Datang",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(bottom = 128.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    16.dp, alignment = Alignment.CenterVertically
                )
            ) {
                MyButton(
                    text = "Register",
                    icon = painterResource(id = R.drawable.outline_edit_24),
                    onClick = onRegisterButtonClicked
                )
                MyOutlinedButton(
                    text = "Login",
                    icon = painterResource(id = R.drawable.login_24),
                    onClick = onLoginButtonClicked
                )
            }
            Text(
                text = "Terms of service and Privacy Policy",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(top = 64.dp)
            )
        }
    }
}

/*
 * Commons components
 */
@Composable
fun MyButton(text: String, icon: Painter, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(180.dp)
            .height(56.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Icon(
                painter = icon,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun MyOutlinedButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .width(180.dp)
            .height(56.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Icon(
                painter = icon,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview(
) {
    WelcomeScreen(
        onRegisterButtonClicked = {},
        onLoginButtonClicked = {}
    )
}