package com.ruben.composition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ruben.composition.Destination.Home
import com.ruben.composition.Destination.LiveNowCarousel
import com.ruben.composition.screens.Home
import com.ruben.composition.screens.LiveNowCarousel

/**
 * Created by Ruben Quadros on 10/08/21
 **/
@Composable
fun CompositionApp() {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }

    NavHost(navController = navController, startDestination = Home) {
        composable(Home) {
            Home(actions.openLiveNowCarousel)
        }

        composable(LiveNowCarousel) {
            LiveNowCarousel {
                actions.navigateUp
            }
        }
    }
}