package com.ruben.composition.screens.exit

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.ruben.composition.R
import com.ruben.composition.components.BackButtonAppBar
import com.ruben.composition.ui.theme.SettingColor
import com.ruben.composition.ui.theme.SettingValueColor
import kotlinx.coroutines.launch

/**
 * Created by Ruben Quadros on 19/08/21
 **/
@ExperimentalMaterialApi
@Composable
fun ExitScreen(
    navigateBack: () -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Surface(color = Color.Black) {
        BottomSheetScaffold(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding(),
            sheetContent = { ExitBottomSheet(navigateBack = navigateBack) },
            scaffoldState = bottomSheetScaffoldState,
            topBar = {
                BackButtonAppBar(
                    title = stringResource(id = R.string.add_people),
                    navigateBack = navigateBack
                )
            },
            sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            sheetBackgroundColor = Color.Black,
            sheetPeekHeight = 0.dp,
            content = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                            } else {
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }
                            }
                        }) {
                        Text(text = stringResource(id = R.string.exit_stream))
                    }
                }
            }
        )
    }
}

@Composable
fun ExitBottomSheet(navigateBack: () -> Unit) {
    Column {
        Image(
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(56.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
        )
        
        Text(
            text = stringResource(id = R.string.follow_me),
            color = SettingColor,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = SettingColor,
                contentColor = Color.Black
            ),
            onClick = {
                //api call to follow
            }) {
            Text(
                text = stringResource(id = R.string.follow_and_leave)
            )
        }
        
        Text(
            text = stringResource(id = R.string.leave),
            color = SettingValueColor,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    //exit
                    navigateBack.invoke()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExitBottomSheetPreview() {
    ExitBottomSheet {

    }
}