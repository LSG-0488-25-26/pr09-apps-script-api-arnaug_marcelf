package com.example.script_api.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationScreens(val route: String,
                                     val icon: ImageVector
){
    object List: BottomNavigationScreens(Routes.Home.route, Icons.Filled.Public)
    object Add: BottomNavigationScreens(Routes.AddPandemic.route, Icons.Filled.Add)
    object Me: BottomNavigationScreens(Routes.Me.route, Icons.Filled.Person)
}