package com.qaraniraka.myapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qaraniraka.myapplication.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit = {},
    onProfileEditClick: () -> Unit = {}
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            ListItem(
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.person_24),
                        contentDescription = null
                    )
                },
                headlineContent = { Text(text = "Edit Profil") },
                modifier = Modifier
                    .clickable { onProfileEditClick() }
            )
            ListItem(
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_logout_24),
                        contentDescription = null
                    )
                },
                headlineContent = { Text(text = "Logout") },
                modifier = Modifier
                    .clickable { onLogoutClick() }
            )
        }
    }
}