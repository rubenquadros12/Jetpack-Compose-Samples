package com.ruben.composition.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable

/**
 * Created by Ruben Quadros on 10/08/21
 **/
@Composable
fun AppBar(title: String) {
    TopAppBar(
        title = { Text(text = title) }
    )
}

@Composable
fun BackButtonAppBar(title: String, navigateBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = { IconButton(onClick = navigateBack) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Button")
        } }
    )
}