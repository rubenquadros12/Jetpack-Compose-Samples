package com.ruben.composition

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ruben.composition.Destination.BottomSheet
import com.ruben.composition.Destination.Home
import com.ruben.composition.Destination.LiveNowCarousel
import com.ruben.composition.screens.BottomSheetScreen
import com.ruben.composition.screens.Home
import com.ruben.composition.screens.LiveNowCarousel

/**
 * Created by Ruben Quadros on 10/08/21
 **/
@ExperimentalMaterialApi
@Composable
fun CompositionApp() {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }

    NavHost(navController = navController, startDestination = Home) {
        composable(Home) {
            Home(actions.openLiveNowCarousel, actions.openBottomSheetScreen)
        }

        composable(LiveNowCarousel) {
            LiveNowCarousel(actions.navigateUp)
        }

        composable(BottomSheet) {
            BottomSheetScreen(actions.navigateUp)
        }
    }
}