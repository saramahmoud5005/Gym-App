package com.example.gymapp

import GymsViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymapp.ui.theme.GymAppTheme
import com.example.gymapp.ui.theme.Purple80
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GymsScreen(){
    //lazyColumn is like recyclerView but Column is like listView
    val vm:GymsViewModel = viewModel()

//    LaunchedEffect( //this prevent calling network request every recomposition of gymScreen and call request when launching only (first time or when configuration change
//        key1 = "request_gyms_list",
//        block = {
//            vm.getGyms()
//        }
//    )

    // remember for save state when recomposition occurred but rememberSaveable used to save data state when configuration change like rotate screen

    LazyColumn(){
        items(vm.state){ gym->
            GymItem(gym){ gymId->
                vm.toggleFavouriteState(gymId)
            }
        }
    }
//    Column(Modifier.verticalScroll(rememberScrollState())) {
//        listOfGym.forEach {
//            GymItem(it)
//        }
//    }
}

@Composable
fun GymItem(gym: Gym, onClick: (Int) -> Unit) {

    val icon = if(gym.isFavourite){
        Icons.Filled.Favorite
    }else{
        Icons.Filled.FavoriteBorder
    }

    Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), modifier = Modifier.padding(8.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)){
            DefaultIcon(Icons.Filled.Place,Modifier.weight(0.15f))
            GymDetails(gym,Modifier.weight(0.70f))
            DefaultIcon(icon,Modifier.weight(0.15f)){
                onClick(gym.id)
            }
        }
    }
}

@Composable
fun DefaultIcon(icon:ImageVector, modifier: Modifier, onClick:()->Unit = {}) {
    Image(
        imageVector = icon,
        contentDescription = "Favorite Icon",
        Modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
            androidx.compose.ui.graphics.Color.DarkGray),
    )
}

@Composable
fun GymDetails(gym: Gym,modifier: Modifier) {
    Column(modifier=modifier) {
        Text(
            text = gym.name,
            style = MaterialTheme.typography.headlineSmall,
            color = Purple80
        )
        CompositionLocalProvider(LocalContentColor provides LocalContentColor.current.copy(alpha = 0.4f)) {
            Text(text = gym.place)
        }
    }
}

@Preview
@Composable
fun _GymScreenPreview(){
    GymAppTheme {
        GymsScreen()
    }
}