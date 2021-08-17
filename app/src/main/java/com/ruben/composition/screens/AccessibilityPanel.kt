package com.ruben.composition.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ruben.composition.R
import com.ruben.composition.ui.theme.Red

/**
 * Created by Ruben Quadros on 16/08/21
 **/
@Composable
fun AccessibilityPanelScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout {
            val (background, close, live, panel) = createRefs()

            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(background) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "Close",
                modifier = Modifier.constrainAs(close) {
                    top.linkTo(parent.top, margin = 80.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Red)
                    .constrainAs(live) {
                        top.linkTo(parent.top, margin = 45.dp)
                        end.linkTo(parent.end, margin = 8.dp)
                    }
            ) {
                Text(
                    modifier = Modifier.padding(3.dp),
                    text = stringResource(id = R.string.all_live),
                    color = Color.White
                )
            }

            Panel(
                modifier = Modifier.constrainAs(panel) {
                    top.linkTo(live.bottom, margin = 8.dp)
                    end.linkTo(parent.end, margin = 3.dp)
                    width = Dimension.wrapContent
                }
            )
        }
    }
}

@Composable
fun Panel(modifier: Modifier = Modifier) {
    val isExpand = remember {
        mutableStateOf(false)
    }
    Column(modifier = modifier.animateContentSize()) {
        ConstraintLayout(modifier = Modifier
            .padding(bottom = 8.dp)
            .align(Alignment.CenterHorizontally)) {
            val (flipImage, flipText) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_flip),
                contentDescription = "Flip Camera",
                modifier = Modifier
                    .size(28.dp)
                    .constrainAs(flipImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Text(
                text = stringResource(id = R.string.flip_camera),
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(flipText) {
                    top.linkTo(flipImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }

        ConstraintLayout(modifier = Modifier
            .padding(vertical = 8.dp)
            .align(Alignment.CenterHorizontally)
        ) {
            val (micImage, micText) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_mic),
                contentDescription = "Mute Mic",
                modifier = Modifier
                    .size(28.dp)
                    .constrainAs(micImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Text(
                text = stringResource(id = R.string.all_mic),
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(micText) {
                    top.linkTo(micImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }

        ConstraintLayout(modifier = Modifier
            .padding(vertical = 8.dp)
            .align(Alignment.CenterHorizontally)
        ) {
            val (cameraImage, cameraText) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_video),
                contentDescription = "Mute Camera",
                modifier = Modifier
                    .size(28.dp)
                    .constrainAs(cameraImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Text(
                text = stringResource(id = R.string.all_camera),
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(cameraText) {
                    top.linkTo(cameraImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }

        if (isExpand.value) {
            ConstraintLayout(modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
            ) {
                val (cameraImage, cameraText) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.ic_flash),
                    contentDescription = "Flash",
                    modifier = Modifier
                        .size(28.dp)
                        .constrainAs(cameraImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = stringResource(id = R.string.all_flash),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(cameraText) {
                        top.linkTo(cameraImage.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }

            ConstraintLayout(modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
            ) {
                val (cameraImage, cameraText) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.ic_lenses),
                    contentDescription = "Lenses",
                    modifier = Modifier
                        .size(28.dp)
                        .constrainAs(cameraImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = stringResource(id = R.string.all_lenses),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(cameraText) {
                        top.linkTo(cameraImage.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }

            ConstraintLayout(modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
            ) {
                val (cameraImage, cameraText) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(28.dp)
                        .constrainAs(cameraImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = stringResource(id = R.string.all_settings),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(cameraText) {
                        top.linkTo(cameraImage.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        }

        Image(
            modifier = Modifier
                .size(24.dp)
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    isExpand.value = !isExpand.value
                },
            painter = painterResource(id = if (isExpand.value) R.drawable.ic_up else R.drawable.ic_down),
            contentDescription = "Expand"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccessibilityPreview() {
    AccessibilityPanelScreen()
}