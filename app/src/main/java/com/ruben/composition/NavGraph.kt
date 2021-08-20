package com.ruben.composition

import androidx.navigation.NavHostController
import com.ruben.composition.Destination.AccessibilityPanel
import com.ruben.composition.Destination.AddPeople
import com.ruben.composition.Destination.BottomSheet
import com.ruben.composition.Destination.CommentsFilter
import com.ruben.composition.Destination.Exit
import com.ruben.composition.Destination.KeyboardAdjust
import com.ruben.composition.Destination.LiveNowCarousel
import com.ruben.composition.Destination.PaidPromo
import com.ruben.composition.Destination.Share

/**
 * Created by Ruben Quadros on 10/08/21
 **/
object Destination {
    const val Home = "home"
    const val LiveNowCarousel = "liveNow"
    const val BottomSheet = "bottomSheet"
    const val CommentsFilter = "commentsFilter"
    const val PaidPromo = "paidPromo"
    const val AccessibilityPanel = "accessibilityPanel"
    const val KeyboardAdjust  = "keyboardAdjust"
    const val AddPeople = "addPeople"
    const val Exit = "exit"
    const val Share = "share"
}

class Actions(navHostController: NavHostController) {
    val navigateUp: () -> Unit = {
        navHostController.navigateUp()
    }

    val openLiveNowCarousel: () -> Unit = {
        navHostController.navigate(LiveNowCarousel)
    }

    val openBottomSheetScreen: () -> Unit = {
        navHostController.navigate(BottomSheet)
    }

    val openCommentsFilter: () -> Unit = {
        navHostController.navigate(CommentsFilter)
    }

    val openPaidPromo: () -> Unit = {
        navHostController.navigate(PaidPromo)
    }

    val openAccessibilityPanel: () -> Unit = {
        navHostController.navigate(AccessibilityPanel)
    }

    val openKeyBoardAdjustScreen: () -> Unit = {
        navHostController.navigate(KeyboardAdjust)
    }

    val openAddPeopleScreen: () -> Unit = {
        navHostController.navigate(AddPeople)
    }

    val openExitScreen: () -> Unit = {
        navHostController.navigate(Exit)
    }

    val openShareScreen: () -> Unit = {
        navHostController.navigate(Share)
    }
}