package com.example.gymapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestUI(){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ){

        Text(
            text = "Login",
            style = TextStyle(Color.Blue),
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 30.dp)
        )
        TextField(
            value = TextFieldValue(
                text = "Email",
            ),
            onValueChange ={
                println(it.text)
            }
        )
        TextField(
            value = TextFieldValue(
                text = "Password",
            ),
            onValueChange ={
                println(it.text)
            },
            Modifier.padding(16.dp)
        )
    }
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 300.dp).fillMaxSize()
    ){
        Icon(
            imageVector = Icons.Rounded.Info,
            contentDescription = "info icon",
            modifier = Modifier
                .padding(8.dp)
                .size(40.dp)
        )
        Icon(
            imageVector = Icons.Rounded.AccountCircle,
            contentDescription = "account icon",
            modifier = Modifier
                .padding(8.dp)
                .size(40.dp)
        )
        Icon(
            imageVector = Icons.Rounded.Email,
            contentDescription = "email icon",
            modifier = Modifier
                .padding(8.dp)
                .size(40.dp)
        )
    }
}

@Preview (showSystemUi = true, showBackground = true)
@Composable
fun _PreviewTestUI(){
    GymAppTheme {
        TestUI()
    }
}