package com.ruben.composition

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.ruben.composition.Destination.AccessibilityPanel
import com.ruben.composition.Destination.AddPeople
import com.ruben.composition.Destination.BottomSheet
import com.ruben.composition.Destination.CommentsFilter
import com.ruben.composition.Destination.DeepLink
import com.ruben.composition.Destination.Exit
import com.ruben.composition.Destination.Home
import com.ruben.composition.Destination.KeyboardAdjust
import com.ruben.composition.Destination.LiveNowCarousel
import com.ruben.composition.Destination.LiveNowUsers
import com.ruben.composition.Destination.Notification
import com.ruben.composition.Destination.PaidPromo
import com.ruben.composition.Destination.Share
import com.ruben.composition.screens.AccessibilityPanelScreen
import com.ruben.composition.screens.DeepLinkScreen
import com.ruben.composition.screens.Home
import com.ruben.composition.screens.KeyboardAdjustScreen
import com.ruben.composition.screens.LiveNowCarousel
import com.ruben.composition.screens.addpeople.AddPeopleScreen
import com.ruben.composition.screens.bottomsheet.BottomSheetScreen
import com.ruben.composition.screens.bottomsheet.BottomViewModel
import com.ruben.composition.screens.bottomsheet.CommentsFilterScreen
import com.ruben.composition.screens.bottomsheet.PaidPromoScreen
import com.ruben.composition.screens.exit.ExitScreen
import com.ruben.composition.screens.sharebottomsheet.ShareScreen
import com.ruben.composition.screens.bottomsheet.*
import com.ruben.composition.screens.notification.NotificationScreen

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
            Home(
                actions.openLiveNowCarousel,
                actions.openBottomSheetScreen,
                actions.openAccessibilityPanel,
                actions.openKeyBoardAdjustScreen,
                actions.openAddPeopleScreen,
                actions.openExitScreen,
                actions.openShareScreen,
                actions.openLiveNowUser,
                actions.openNotificationScreen
            )
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

        composable(KeyboardAdjust) {
            KeyboardAdjustScreen()
        }

        composable(AddPeople) {
            AddPeopleScreen(actions.navigateUp)
        }

        composable(Exit) {
            ExitScreen(actions.navigateUp)
        }

        composable(Share) {
            ShareScreen(actions.navigateUp)
        }

        composable(LiveNowUsers){
            LiveNowBottomSheet()
        }

        composable(Notification) {
            NotificationScreen(actions.navigateUp)
        }

        composable(route = "$DeepLink?name={name}", deepLinks = listOf(navDeepLink {
            uriPattern = "https://ruben.in/{name}"
        })) {
            DeepLinkScreen(it.arguments?.getString("name") ?: "")
        }
    }
}