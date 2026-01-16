package com.example.mobile.presentation

sealed class Screen (val route: String){
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object StudentsScreen: Screen("students_screen")
    object HomeScreen: Screen("home_screen")
    object RequestsScreen: Screen("requests_screen")
    object ProductScreen: Screen("product_screen")
    object ProductTypesScreen: Screen("product_types_screen")
    object StudentReqScreen: Screen("student_req_screen")
    object CreateRequestScreen: Screen("create_request_screen")
    object RequestDetailScreen: Screen("request_detail_screen")
    object StockScreen: Screen("stock_screen")

}