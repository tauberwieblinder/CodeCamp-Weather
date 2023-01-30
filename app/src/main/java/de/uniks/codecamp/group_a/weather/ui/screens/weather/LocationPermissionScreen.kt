package de.uniks.codecamp.group_a.weather.ui.screens.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import de.uniks.codecamp.group_a.weather.R


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionScreen(permissionState: PermissionState) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = 10.dp,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.sunny),
                "",
            )

            Text(
                text = "CodeCamp Wetter App",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Diese App benötigt die Berechtigung, deinen Standort abzufragen.",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(onClick = {
                permissionState.launchPermissionRequest()
            }) {
                Text("Standort freigeben")
            }
        }
    }
}