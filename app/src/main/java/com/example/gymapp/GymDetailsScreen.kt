package com.example.gymapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GymDetailsScreen(){
    val viewModel:GymDetailsViewModel = viewModel()
    val item = viewModel.state.value
    item?.let {

    }
}