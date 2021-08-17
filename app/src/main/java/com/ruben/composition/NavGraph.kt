package com.ruben.composition

import androidx.navigation.NavHostController
import com.ruben.composition.Destination.AccessibilityPanel
import com.ruben.composition.Destination.BottomSheet
import com.ruben.composition.Destination.CommentsFilter
import com.ruben.composition.Destination.LiveNowCarousel
import com.ruben.composition.Destination.PaidPromo

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
}