package com.example.gymapp

import android.graphics.Color
import android.graphics.ColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymapp.ui.theme.GymAppTheme
import com.example.gymapp.ui.theme.Purple80

@Composable
fun GymsScreen(){
    GymItem()
}

@Composable
fun GymItem() {
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), modifier = Modifier.padding(8.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically){
            GymIcon(Icons.Filled.Place,Modifier.weight(0.15f))
            GymDetails(Modifier.weight(0.85f))
        }
    }
}

@Composable
fun GymDetails(modifier: Modifier) {
    Column(modifier=modifier) {
        Text(
            text = "Gym Details",
            style = MaterialTheme.typography.headlineSmall,
            color = Purple80
        )
        CompositionLocalProvider(LocalContentColor provides LocalContentColor.current.copy(alpha = 0.4f)) {
            Text(text = "This is the all details 11111")
        }
    }

}

@Composable
fun GymIcon(place: ImageVector, modifier: Modifier) {
    Image(imageVector = place, contentDescription = "Gym icon", modifier = modifier, colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
        androidx.compose.ui.graphics.Color.DarkGray))

}

@Preview
@Composable
fun _GymScreenPreview(){
    GymAppTheme {
        GymsScreen()
    }
}


