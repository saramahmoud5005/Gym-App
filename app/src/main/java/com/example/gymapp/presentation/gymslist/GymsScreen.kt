package com.example.gymapp.presentation.gymslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gymapp.ui.theme.Purple80
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymapp.Gym

@Composable
fun GymsScreen(onItemClick:(Int) ->Unit){
    val vm: GymsViewModel = viewModel()
    val state = vm.state.value
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ){
        LazyColumn(){
            items(state.gyms){ gym->
                GymItem(
                    gym = gym,
                    onFavouriteIconClick = { vm.toggleFavouriteState(it) },
                    onItemClick ={ id ->
                        onItemClick(id)
                    }
                )
            }
        }
        if(state.isLoading) CircularProgressIndicator()
        state.error?.let { Text(text = it) }
    }

}

@Composable
fun GymItem(gym: Gym, onFavouriteIconClick: (Int) -> Unit, onItemClick:(Int) ->Unit) {

    val icon = if(gym.isFavourite){
        Icons.Filled.Favorite
    }else{
        Icons.Filled.FavoriteBorder
    }

    Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), modifier = Modifier
        .padding(8.dp)
        .clickable { onItemClick(gym.id) }) {
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)){
            DefaultIcon(Icons.Filled.Place,Modifier.weight(0.15f))
            GymDetails(gym, Modifier.weight(0.70f))
            DefaultIcon(icon,Modifier.weight(0.15f)){
                onFavouriteIconClick(gym.id)
            }
        }
    }
}

@Composable
fun DefaultIcon(icon:ImageVector, modifier: Modifier, onClick:()->Unit = {}) {
    Image(
        imageVector = icon,
        contentDescription = "Favorite Icon",
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
            androidx.compose.ui.graphics.Color.DarkGray),
    )
}

@Composable
fun GymDetails(gym: Gym, modifier: Modifier, horizontalAlignment: TextAlign = TextAlign.Start) {
    
    Column(modifier = modifier) {
        Text(
            text = gym.name,
            style = MaterialTheme.typography.headlineSmall,
            color = Purple80,
            modifier= Modifier.fillMaxWidth(),
            textAlign = horizontalAlignment
        )
        CompositionLocalProvider(
            LocalContentColor provides LocalContentColor.current.copy(alpha = 0.4f)
            ) {
            Text(
                text = gym.place,
                modifier = Modifier.fillMaxWidth(),
                textAlign = horizontalAlignment
            )
        }
    }
}
