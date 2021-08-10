package com.ruben.composition

import androidx.navigation.NavHostController
import com.ruben.composition.Destination.LiveNowCarousel

/**
 * Created by Ruben Quadros on 10/08/21
 **/
object Destination {
    const val Home = "home"
    const val LiveNowCarousel = "liveNow"
}

class Actions(navHostController: NavHostController) {
    val navigateUp: () -> Unit = {
        navHostController.navigateUp()
    }

    val openLiveNowCarousel: () -> Unit = {
        navHostController.navigate(LiveNowCarousel)
    }
}