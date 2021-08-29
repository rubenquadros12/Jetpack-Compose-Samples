package com.ruben.composition.screens.bottomsheet

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.ruben.composition.R
import com.ruben.composition.screens.bottomsheet.ButtonStyle.Companion.darkButtonWithRedTextStyle
import com.ruben.composition.screens.bottomsheet.ButtonStyle.Companion.darkButtonWithWhiteTextStyle
import com.ruben.composition.screens.bottomsheet.ButtonStyle.Companion.whiteButtonWithDarkTextStyle
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Preview
@ExperimentalMaterialApi
@Composable
fun LiveNowBottomSheet() {
    val currentUser = User("currentUser", isHost = false, userHandle = "@currentUser", fullName = "Current User", numberOfFollowers = 1000)
    val usersList: List<User> = (0..10).map { User(userId = "userId$it", numberOfFollowers = it + 500L, fullName = "full name $it", userHandle = "@handle$it", isHost = false) }.toMutableList().also { it.add(2, currentUser) }
    val followingUserList: List<User> = usersList.subList(4, 8)
    val state = LiveStreamState(currentUser = currentUser, liveNowUserList = usersList, followingUserList = followingUserList)

    MaterialTheme {
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberBottomSheetScaffoldState()

        BottomSheetScaffold(
                sheetContent = {
                    LiveNowContent(currentUser, state)
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
private fun LiveNowContent(currentUser: User, liveStreamState: LiveStreamState) {
    Column(modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)) {
        Box(modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text("Live Now", color = Color.White, fontSize = 20.sp)
        }

        LazyColumn(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(1.dp)) {
            items(items = liveStreamState.liveNowUserList, itemContent = { item ->
                SwipeableLiveUserEntry(currentUser, item, liveStreamState.followingUserList.any { it.userId == item.userId })
            })
        }

    }
}

@ExperimentalMaterialApi
@Composable
fun SwipeableLiveUserEntry(currentUser: User, liveUser: User, isUserFollowed: Boolean) {
    val squareSize = 72.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, -sizePx to 1) // Maps anchor points (in px) to states

    var modifier = Modifier
            .fillMaxWidth()

    if (currentUser.isHost) {
        modifier = modifier.swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
        )
    }


    Box(
            modifier = modifier
    ) {
        Box(modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF131319)), contentAlignment = Alignment.CenterEnd) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
                    .width(72.dp)
                    .height(72.dp)
                    .clickable { }) {
                Icon(Icons.Default.Delete, contentDescription = "Block the user", tint = Color(0xFFFC3361))
                Text(text = "Block", color = Color(0xFFFC3361))
            }
        }

        LiveUserEntry(swipeableState, currentUser, liveUser, isUserFollowed)
    }
}

@ExperimentalMaterialApi
@Composable
private fun LiveUserEntry(swipeableState: SwipeableState<Int>, currentUser: User, liveUser: User, isUserFollowed: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
            .fillMaxWidth()
            .background(Color(0xFF000000))
            .wrapContentHeight()
            .zIndex(1.0f)
            .clickable { }
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
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

        if (!liveUser.isHost) {
            var actionText = ""
            val buttonStyle: ButtonStyle

            when {
                currentUser.isHost && liveUser.userId != currentUser.userId -> {
                    actionText = "Remove"
                    buttonStyle = darkButtonWithWhiteTextStyle()
                }
                currentUser.userId == liveUser.userId -> {
                    actionText = "Quit"
                    buttonStyle = darkButtonWithRedTextStyle()
                }
                isUserFollowed -> {
                    actionText = "Following"
                    buttonStyle = darkButtonWithWhiteTextStyle()
                }
                else -> {
                    actionText = "Follow"
                    buttonStyle = whiteButtonWithDarkTextStyle()
                }
            }

            Button(onClick = { }, colors = ButtonDefaults.textButtonColors(
                    backgroundColor = buttonStyle.buttonBackgroundColor
            ), modifier = Modifier.width(110.dp)) {
                Text(text = actionText, color = buttonStyle.textColor)
            }
        }
    }
}

data class ButtonStyle(val buttonBackgroundColor: Color, val textColor: Color) {
    companion object {
        fun darkButtonWithWhiteTextStyle() = ButtonStyle(buttonBackgroundColor = Color(0xFF131319), textColor = Color(0xFFD7D7D8))
        fun darkButtonWithRedTextStyle() = ButtonStyle(buttonBackgroundColor = Color(0xFF131319), textColor = Color(0xFFFC3361))
        fun whiteButtonWithDarkTextStyle() = ButtonStyle(buttonBackgroundColor = Color(0xFFD7D7D8), textColor = Color(0xFF131319))
    }
}

data class LiveStreamState(val currentUser: User, val liveNowUserList: List<User>, val followingUserList: List<User>)
data class User(val userId: String = "", val userHandle: String = "", val fullName: String = "", val numberOfFollowers: Long = 0L, val isHost: Boolean = false)