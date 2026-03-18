package com.example.script_api.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.script_api.viewModel.ScaffoldViewModel

@Composable
fun MyBottomBar(
    myViewModel: ScaffoldViewModel,
    navigationController: NavHostController
) {
    val bottomNavigationItems by myViewModel
        .bottomNavigationItems
        .observeAsState(emptyList())


    NavigationBar(
        containerColor = Color.DarkGray,
        contentColor = Color.White
    ) {
        val navBackEntry by navigationController.currentBackStackEntryAsState()
        val currentRoute = navBackEntry?.destination?.route

        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null, tint = Color.White) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navigationController.navigate(item.route)
                    }
                }
            )
        }
    }
}