package com.example.gymapp.presentation.gymdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymapp.presentation.gymslist.DefaultIcon
import com.example.gymapp.presentation.gymslist.GymDetails
import com.example.gymapp.ui.theme.GymAppTheme

@Composable
fun GymDetailsScreen(){
    val viewModel: GymDetailsViewModel = viewModel()
    val item = viewModel.state.value
//    val item =Gym(1,"gym name","gym address", true,true)
    item?.let {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(16.dp)) {
            DefaultIcon(
                icon = Icons.Filled.Place,
                modifier = Modifier.padding(bottom = 32.dp, top = 32.dp),
            )
            GymDetails(
                gym = item,
                modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth(),
                horizontalAlignment = TextAlign.Center
            )
            Text(
                text = if(item.isOpen) "Gym is open" else "Gym is close",
                color = if(item.isOpen) Color.Green else Color.Red
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun _GymDetailsScreenPreview(){
    GymAppTheme {
        GymDetailsScreen()
    }
}