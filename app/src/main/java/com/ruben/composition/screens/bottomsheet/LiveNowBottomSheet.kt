package com.ruben.composition.screens.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.ruben.composition.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Preview
@ExperimentalMaterialApi
@Composable
fun LiveNowBottomSheet() {
    MaterialTheme {

        val scope = rememberCoroutineScope()
        val scaffoldState = rememberBottomSheetScaffoldState()

        BottomSheetScaffold(
                sheetContent = {
                    LiveNowContent()
                },
                scaffoldState = scaffoldState,
                sheetPeekHeight = 0.dp,
                sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                sheetBackgroundColor = Color.Black,

                ) {
            Box(Modifier.fillMaxSize()) {
                Button(onClick = {
                    if (scaffoldState.bottomSheetState.isCollapsed) {
                        scope.launch { scaffoldState.bottomSheetState.expand() }
                    } else {
                        scope.launch { scaffoldState.bottomSheetState.collapse() }
                    }
                }, modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)) {
                    Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Show")
                }

            }

        }
    }

}

@ExperimentalMaterialApi
@Composable
private fun LiveNowContent() {
    Column(modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)) {
        Box(modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text("Live Now", color = Color.White, fontSize = 20.sp)
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(1.dp)) {
            items(100) {
                SwipeableLiveUserEntry()
            }
        }
    }
}


@ExperimentalMaterialApi
@Preview
@Composable
fun SwipeableSample() {
    val width = 96.dp
    val squareSize = 48.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

    Box(
            modifier = Modifier
                    .width(width)
                    .swipeable(
                            state = swipeableState,
                            anchors = anchors,
                            thresholds = { _, _ -> FractionalThreshold(0.3f) },
                            orientation = Orientation.Horizontal
                    )
                    .background(Color.LightGray)
    ) {
        Box(
                Modifier
                        .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                        .size(squareSize)
                        .background(Color.DarkGray)
        )
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun SwipeableLiveUserEntry() {
    val squareSize = 72.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, -sizePx to 1) // Maps anchor points (in px) to states

    Box(
            modifier = Modifier
                    .fillMaxWidth()
                    .swipeable(
                            state = swipeableState,
                            anchors = anchors,
                            thresholds = { _, _ -> FractionalThreshold(0.3f) },
                            orientation = Orientation.Horizontal
                    )
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF131319)), contentAlignment = Alignment.CenterEnd) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.width(72.dp).height(72.dp).clickable {  }) {
                Icon(Icons.Default.Delete, contentDescription = "Block the user", tint = Color(0xFFFC3361))
                Text(text = "Block", color = Color(0xFFFC3361))
            }
        }
        LiveUserEntry(swipeableState)
    }
}

@ExperimentalMaterialApi
@Composable
private fun LiveUserEntry(swipeableState: SwipeableState<Int>) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
            .fillMaxWidth()
            .background(Color(0xFF000000))
            .wrapContentHeight()
            .zIndex(1.0f)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .clickable { }) {
        Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = colors.onSurface.copy(alpha = 0.2f)

        ) {
            Image(painter = painterResource(id = R.drawable.profile), contentDescription = "")
        }
        Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1.0f)) {
            Text("Full Name", color = Color(0xFFD7D7D8))
            Text("@handle", color = Color(0xFF8A8A8F))
            Text("Role", color = Color(0xFF8A8A8F))
        }
        Button(onClick = { }, colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color(0xFF131319)
        )) {
            Text(text = "Remove", color = Color(0xFFD7D7D8))
        }
    }
}