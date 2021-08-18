package com.ruben.composition.components

import androidx.compose.foundation.background
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.ruben.composition.R
import com.ruben.composition.ui.theme.SettingColor

/**
 * Created by Ruben Quadros on 10/08/21
 **/
@Composable
fun AppBar(title: String) {
    TopAppBar(
        modifier = Modifier.background(Color.Transparent),
        title = { Text(text = title) }
    )
}

@Composable
fun BackButtonAppBar(title: String, navigateBack: () -> Unit) {
    TopAppBar(
        modifier = Modifier.background(Color.Transparent),
        title = { Text(text = title) },
        navigationIcon = { IconButton(onClick = navigateBack) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Button")
        } }
    )
}

@Composable
fun MojAppBar(title: String, navigateBack: () -> Unit) {
    TopAppBar(
        backgroundColor = Color.Black,
        title = { Text(text = title, color = SettingColor) },
        navigationIcon = { IconButton(onClick = navigateBack) {
            Icon(painter = painterResource(id = R.drawable.ic_back_button), contentDescription = "Back Button", tint = SettingColor)
        } }
    )
}