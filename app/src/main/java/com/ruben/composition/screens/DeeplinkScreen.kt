package com.ruben.composition.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Created by Ruben Quadros on 13/09/21
 **/
@Composable
fun DeepLinkScreen(name: String) {

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = name,
            modifier = Modifier.align(Alignment.Center)
        )
    }

}