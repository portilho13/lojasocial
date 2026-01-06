package com.example.mobile.presentation

sealed class Screen (val route: String){
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
//    object ExerciseDetailScreen: Screen("exercise_detail_screen")
//    object ExerciseSearchScreen: Screen("exercise_search_screen")
//    object FavoriteListScreen: Screen("favorite_list_screen")
}