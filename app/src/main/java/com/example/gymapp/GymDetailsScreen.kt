package com.example.gymapp

import android.graphics.drawable.Icon
import android.text.Layout.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymapp.ui.theme.GymAppTheme

@Composable
fun GymDetailsScreen(){
    val viewModel:GymDetailsViewModel = viewModel()
    val item = listOfGym
    item?.let {
        Column {
            DefaultIcon(
                icon = Icons.Filled.Place,
                modifier = Modifier.padding(bottom = 32.dp, top = 32.dp),
            )
            GymDetails(gym = item.get(3), modifier = Modifier.padding(all = 30.dp))
        }
    }
}
@Preview
@Composable
fun _GymDetailsScreenPreview(){
    GymAppTheme {
        GymDetailsScreen()
    }
}