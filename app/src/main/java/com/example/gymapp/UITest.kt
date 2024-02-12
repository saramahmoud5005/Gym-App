package com.example.gymapp

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
        var email by rememberSaveable {
            mutableStateOf("")
        }
        TextField(
            value = email,
            onValueChange ={
                email = it
                println(it)
            }
        )
        var password by rememberSaveable {
            mutableStateOf("")
        }
        TextField(
            value = password,
            onValueChange ={
                password = it
                println(it)
            },
            Modifier.padding(16.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.width(280.dp).height(50.dp),
            enabled = true,
        ) {
            Text(
                text = "Login",
            )
        }

        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 10.dp)
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

        Text(
            text = "Forget Password",
            modifier = Modifier
                .padding(10.dp)
                .clickable(
                    enabled = true,
                    onClick = {
                        Log.d("Test", "TestUI: ")
                    }
                ),
//            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color.Black,
            ),
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