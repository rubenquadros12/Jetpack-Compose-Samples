package com.ruben.composition.screens.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruben.composition.R
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun SendCohostRequestBottomSheet(cohostInviteRequest: InviteRequest) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
            sheetContent = {
                Column(modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterHorizontally)) {
                    Box(modifier = Modifier
                            .padding(top = 16.dp, bottom = 16.dp)
                            .wrapContentHeight()
                            .width(160.dp)
                            .align(CenterHorizontally), contentAlignment = Alignment.Center) {
                        // user 2
                        Box(modifier = Modifier.align(Alignment.CenterEnd), contentAlignment = Alignment.Center) {
                            Surface(
                                    shape = CircleShape,
                                    modifier = Modifier
                                            .clip(CircleShape)
                                            .size(96.dp)
                                            .background(color = Color(0xFF131319))
                                            .padding(2.dp),
                                    color = Color.Black

                            ) {
                                Image(painter = painterResource(id = R.drawable.profile), contentDescription = "")
                            }
                        }
                        // user 1
                        Box(modifier = Modifier.align(Alignment.CenterStart), contentAlignment = Alignment.Center) {
                            Surface(
                                    shape = CircleShape,
                                    modifier = Modifier
                                            .clip(CircleShape)
                                            .size(96.dp)
                                            .background(color = Color(0xFF131319))
                                            .padding(2.dp),
                                    color = Color.White

                            ) {
                                Image(painter = painterResource(id = R.drawable.profile), contentDescription = "")
                            }
                        }
                    }

                    var isFirstButtonEnabled = true
                    val firstActionButtonStyle: ButtonStyle
                    val secondActionButtonStyle: ButtonStyle
                    var firstActionButtonText = ""
                    var secondActionButtonText = ""
                    var titleText = ""
                    var descText = ""

                    when (cohostInviteRequest) {
                        is InviteRequest.CancelInviteRequest -> {
                            isFirstButtonEnabled = true
                            firstActionButtonStyle = ButtonStyle.darkButtonWithWhiteTextStyle()
                            secondActionButtonStyle = ButtonStyle.darkButtonWithDarkTextStyle()
                            firstActionButtonText = "Cancel Request"
                            secondActionButtonText = "Close"
                            titleText = "Request sent to go live with \n${cohostInviteRequest.user.userHandle}"
                            descText = "Both of your followers can watch, and some of your followers will get notified. \nN users are watching this live right now."
                        }
                        is InviteRequest.SendInviteRequest -> {
                            isFirstButtonEnabled = true
                            firstActionButtonStyle = ButtonStyle.whiteButtonWithDarkTextStyle()
                            secondActionButtonStyle = ButtonStyle.darkButtonWithDarkTextStyle()
                            firstActionButtonText = "Send Request"
                            secondActionButtonText = "Cancel"
                            titleText = "Request to go live with \n${cohostInviteRequest.user.userHandle}"
                            descText = "Both of your followers can watch, and some of your followers will get notified. \nN users are watching this live right now."
                        }
                        is InviteRequest.SendInviteRequested -> {
                            isFirstButtonEnabled = false
                            firstActionButtonStyle = ButtonStyle.darkButtonWithWhiteTextStyle()
                            secondActionButtonStyle = ButtonStyle.darkButtonWithDarkTextStyle()
                            firstActionButtonText = "Request Sent"
                            secondActionButtonText = "Close"
                            titleText = "Request sent to go live with \n${cohostInviteRequest.user.userHandle}"
                            descText = "Both of your followers can watch, and some of your followers will get notified. \nN users are watching this live right now."
                        }
                    }

                    Text(text = titleText, color = Color(0xFFD7D7D8), fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.align(CenterHorizontally))
                    Text(text = descText, color = Color(0xFF8A8A8F), fontSize = 15.sp, textAlign = TextAlign.Center, modifier = Modifier
                            .fillMaxWidth()
                            .align(CenterHorizontally)
                            .padding(top = 16.dp, start = 24.dp, end = 24.dp))

                    // send request
                    Button(onClick = { }, colors = ButtonDefaults.textButtonColors(
                            backgroundColor = firstActionButtonStyle.buttonBackgroundColor
                    ), modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 24.dp, end = 24.dp), enabled = isFirstButtonEnabled) {
                        Text(text = firstActionButtonText, color = firstActionButtonStyle.textColor)
                    }
                    // cancel
                    Button(onClick = { }, colors = ButtonDefaults.textButtonColors(
                            backgroundColor = secondActionButtonStyle.buttonBackgroundColor
                    ), modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)) {
                        Text(text = secondActionButtonText, color = secondActionButtonStyle.textColor)
                    }
                }
            },
            scaffoldState = scaffoldState,
            sheetPeekHeight = 400.dp,
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
                Text("Show Request to Go Live")
            }
        }
    }
}

@Preview(name = "SendInviteRequest")
@ExperimentalMaterialApi
@Composable
fun SendCoHostInviteRequestPreview() {
    val currentUser = User("currentUser", isHost = true, userHandle = "@currentUser", fullName = "Current User", numberOfFollowers = 1000)
    val invitedUser = User("invitedUser", isHost = false, userHandle = "@invitedUser", fullName = "Invited User", numberOfFollowers = 1001)
    MaterialTheme {
        SendCohostRequestBottomSheet(InviteRequest.SendInviteRequest(currentUser = currentUser, user = invitedUser))
    }
}

@Preview(name = "SendInviteRequested")
@ExperimentalMaterialApi
@Composable
fun SendCoHostInviteRequestedPreview() {
    val currentUser = User("currentUser", isHost = true, userHandle = "@currentUser", fullName = "Current User", numberOfFollowers = 1000)
    val invitedUser = User("invitedUser", isHost = false, userHandle = "@invitedUser", fullName = "Invited User", numberOfFollowers = 1001)
    MaterialTheme {
        SendCohostRequestBottomSheet(InviteRequest.SendInviteRequested(currentUser = currentUser, user = invitedUser))
    }
}

@Preview(name = "CancelInviteRequest")
@ExperimentalMaterialApi
@Composable
fun SendCoHostCancelInviteRequestPreview() {
    val currentUser = User("currentUser", isHost = true, userHandle = "@currentUser", fullName = "Current User", numberOfFollowers = 1000)
    val invitedUser = User("invitedUser", isHost = false, userHandle = "@invitedUser", fullName = "Invited User", numberOfFollowers = 1001)
    MaterialTheme {
        SendCohostRequestBottomSheet(InviteRequest.CancelInviteRequest(currentUser = currentUser, user = invitedUser))
    }
}

sealed class InviteRequest {
    data class SendInviteRequest(val currentUser: User, val user: User) : InviteRequest()
    data class SendInviteRequested(val currentUser: User, val user: User) : InviteRequest()
    data class CancelInviteRequest(val currentUser: User, val user: User) : InviteRequest()

}

