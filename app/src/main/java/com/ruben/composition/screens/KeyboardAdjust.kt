package com.ruben.composition.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ruben.composition.R
import com.ruben.composition.ui.theme.CommentETColor
import com.ruben.composition.ui.theme.SettingValueColor

/**
 * Created by Ruben Quadros on 17/08/21
 **/
@Composable
fun KeyboardAdjustScreen() {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(Color.Transparent)
    }


    val textState = remember {
        mutableStateOf(TextFieldValue())
    }

    val navBarInsets = LocalWindowInsets.current.navigationBars
    val statusBarInsets = LocalWindowInsets.current.statusBars
    val density = LocalDensity.current

    Surface(color = Color.Black) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize().navigationBarsWithImePadding()
        ) {

            val (background, comments, bottomBar) = createRefs()

            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Background",
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(background) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color.Black)
                    .constrainAs(bottomBar) {
                        //bottom.linkTo(parent.bottom, margin = (insets.bottom/density.density).dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.value(72.dp)
                    }) {

                TextField(
                    value = textState.value,
                    onValueChange = { textState.value = it },
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .weight(3f, true)
                        .background(CommentETColor),
                    placeholder = {
                        Text(
                            text = "Comment here...",
                            color = SettingValueColor
                        )
                    },
                    textStyle = TextStyle(color = SettingValueColor),
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                )
                Row(
                    modifier = Modifier
                        .weight(1f, true)
                        .fillMaxHeight()
                        .background(Color.Black),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_video),
                        contentDescription = "Requests",
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = "Share",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KeyboardAdjustScreenPreview() {
    KeyboardAdjustScreen()
}