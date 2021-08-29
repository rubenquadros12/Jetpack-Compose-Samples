package com.ruben.composition.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.ruben.composition.R
import com.ruben.composition.components.AppBar
import com.ruben.composition.ui.theme.Purple500

/**
 * Created by Ruben Quadros on 10/08/21
 **/
@Composable
fun Home(
    openLiveNowCarousel: () -> Unit,
    openBottomSheet: () -> Unit,
    openAccessibilityPanel: () -> Unit,
    openKeyboardAdjustScreen: () -> Unit,
    openAddPeople: () -> Unit,
    openExitStream: () -> Unit,
    openShareScreen: () -> Unit,
    openLiveNowUsersScreen: () -> Unit
) {
    Surface(color = Purple500) {
        Scaffold(
            modifier = Modifier.statusBarsPadding(),
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
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = openBottomSheet
                    ) {
                        Text(text = stringResource(id = R.string.bottom_sheet))
                    }
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = openAccessibilityPanel
                    ) {
                        Text(text = stringResource(id = R.string.accessibility_panel))
                    }
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = openKeyboardAdjustScreen
                    ) {
                        Text(text = stringResource(id = R.string.keyboard_adjust))
                    }
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = openAddPeople
                    ) {
                        Text(text = stringResource(id = R.string.add_people))
                    }
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = openExitStream
                    ) {
                        Text(text = stringResource(id = R.string.exit))
                    }
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = openShareScreen
                    ) {
                        Text(text = stringResource(id = R.string.share))
                    }
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = openLiveNowUsersScreen
                    ) {
                        Text(text = stringResource(id = R.string.live_now_users))
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home({}, {}, {}, {}, {}, {}, {},{})
}