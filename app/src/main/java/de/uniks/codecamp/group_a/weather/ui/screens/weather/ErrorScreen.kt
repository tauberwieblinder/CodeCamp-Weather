package de.uniks.codecamp.group_a.weather.ui.screens.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(error: String?, onRefresh: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().fillMaxWidth().padding(60.dp),
        verticalArrangement = Arrangement.Center

        ) {
        Text(text = "Couldn't retrieve fresh weather data.\nCheck your internet connection and make sure to enable Location Access.")
        error?.let { // Wenn wir einen error Text haben, zeigen wir diesen zusätzlich an
            Text(text = "Error: ${it}")
        }
        OutlinedButton( // Mit Klick auf den Button wird versucht die Wetterdaten erneut abzurufen
            onClick = onRefresh
        ) {
            Text("Reload")
        }
    }
}