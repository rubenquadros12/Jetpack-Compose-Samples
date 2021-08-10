package com.ruben.composition.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ruben.composition.R
import com.ruben.composition.components.AppBar

/**
 * Created by Ruben Quadros on 10/08/21
 **/
@Composable
fun Home(
    openLiveNowCarousel: () -> Unit
) {
    Scaffold(
        topBar = { AppBar(title = stringResource(id = R.string.app_name)) },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = openLiveNowCarousel
                ) {
                    Text(text = stringResource(id = R.string.live_now_carousel))
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home {

    }
}