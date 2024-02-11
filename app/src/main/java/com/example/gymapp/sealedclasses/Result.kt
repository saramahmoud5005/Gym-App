package com.example.gymapp.sealedclasses

sealed class Result{
    data class Success(val data:String):Result()
    data class Error(val message:String):Result()
    object Loading:Result()
}
