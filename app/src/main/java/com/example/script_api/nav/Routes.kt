package com.example.script_api.nav


sealed class Routes(val route: String) {
    object Home : Routes("PandemicsListScreen")
    object PandemicsDetailScreen : Routes("PandemicsListScreen/{pandemicName}") {
        fun createRoute(pandemicName: String) = "PandemicsListScreen/$pandemicName"
    }
    object AddPandemic : Routes("addPandemic")
    object Me : Routes("me")

    object Login : Routes("login")
    object Register : Routes("register")
}