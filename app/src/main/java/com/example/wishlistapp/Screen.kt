package com.example.wishlistapp

sealed class Screen (val route : String){
    object Home : Screen ("Home_Screen")
    object Add : Screen ("Add_Screen")
}