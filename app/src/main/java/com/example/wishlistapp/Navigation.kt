package com.example.wishlistapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun Nagivation(viewModel: WishViewModel = viewModel(), navController: NavHostController = rememberNavController()){
    NavHost(navController = navController,
        startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route){
            HomeView(navController,viewModel)
        }
        composable(route = Screen.Add.route+"/{id}",
            arguments = listOf(
                navArgument("id"){
                    defaultValue = 0L
                    type = NavType.LongType
                    nullable = false
                }
            )
        ){NavBackStackEntry->
            val id = NavBackStackEntry.arguments?.getLong("id") ?: 0L
            AddEditDetailsView(id = id,
                viewModel = viewModel,
                navController = navController)
        }
    }
}



