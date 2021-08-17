package com.ruben.composition

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ruben.composition.Destination.AccessibilityPanel
import com.ruben.composition.Destination.BottomSheet
import com.ruben.composition.Destination.CommentsFilter
import com.ruben.composition.Destination.Home
import com.ruben.composition.Destination.LiveNowCarousel
import com.ruben.composition.Destination.PaidPromo
import com.ruben.composition.screens.AccessibilityPanelScreen
import com.ruben.composition.screens.bottomsheet.BottomSheetScreen
import com.ruben.composition.screens.Home
import com.ruben.composition.screens.LiveNowCarousel
import com.ruben.composition.screens.bottomsheet.BottomViewModel
import com.ruben.composition.screens.bottomsheet.CommentsFilterScreen
import com.ruben.composition.screens.bottomsheet.PaidPromoScreen

/**
 * Created by Ruben Quadros on 10/08/21
 **/
@ExperimentalMaterialApi
@Composable
fun CompositionApp() {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }
    val sharedViewModel: BottomViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Home) {
        composable(Home) {
            Home(actions.openLiveNowCarousel, actions.openBottomSheetScreen, actions.openAccessibilityPanel)
        }

        composable(LiveNowCarousel) {
            LiveNowCarousel(actions.navigateUp)
        }

        composable(BottomSheet) {
            BottomSheetScreen(actions.navigateUp, actions.openPaidPromo, actions.openCommentsFilter, sharedViewModel)
        }

        composable(CommentsFilter) {
            CommentsFilterScreen(actions.navigateUp)
        }

        composable(PaidPromo) {
            PaidPromoScreen(actions.navigateUp, sharedViewModel)
        }

        composable(AccessibilityPanel) {
            AccessibilityPanelScreen()
        }
    }
}