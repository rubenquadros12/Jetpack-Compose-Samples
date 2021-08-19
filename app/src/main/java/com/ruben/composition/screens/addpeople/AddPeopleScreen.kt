package com.ruben.composition.screens.addpeople

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.ruben.composition.R
import com.ruben.composition.components.BackButtonAppBar
import com.ruben.composition.components.LoadingView
import com.ruben.composition.data.MockData
import com.ruben.composition.model.JoinRequestEntity
import com.ruben.composition.model.Status
import com.ruben.composition.parseCount
import com.ruben.composition.ui.theme.CommentETColor
import com.ruben.composition.ui.theme.SettingColor
import com.ruben.composition.ui.theme.SettingValueColor
import kotlinx.coroutines.launch

/**
 * Created by Ruben Quadros on 18/08/21
 **/
@ExperimentalMaterialApi
@Composable
fun AddPeopleScreen(
    navigateBack: () -> Unit,
    addPeopleViewModel: AddPeopleViewModel = hiltViewModel()
) {

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val isStreamEmpty = remember {
        mutableStateOf(false)
    }
    Surface(color = Color.Black) {
        BottomSheetScaffold(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding(),
            sheetContent = { AddPeopleBottomSheet() },
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
                                    isStreamEmpty.value = true
                                    addPeopleViewModel.getEmptyList()
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                            } else {
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }
                            }
                        }) {
                        Text(text = stringResource(id = R.string.show_bottom_sheet_empty))
                    }

                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                coroutineScope.launch {
                                    isStreamEmpty.value = false
                                    addPeopleViewModel.getJoinRequests()
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                            } else {
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }
                            }
                        }) {
                        Text(text = stringResource(id = R.string.show_bottom_sheet_people))
                    }
                }
            }
        )
    }
}

@Composable
fun AddPeopleBottomSheet(addPeopleViewModel: AddPeopleViewModel = hiltViewModel()) {

    val uiState = addPeopleViewModel.uiState.collectAsState()
    when (uiState.value) {
        is AddPeopleState.InitialState -> {
            LoadingView(modifier = Modifier.height(512.dp))
        }
        is AddPeopleState.JoinRequest -> {
            val joinRequest = (uiState.value as AddPeopleState.JoinRequest)
            val requests = joinRequest.requests
            if (joinRequest.status == Status.SUCCESS && requests.isEmpty()) {
                AddPeopleEmptyContent(modifier = Modifier.height(512.dp))
            } else if (joinRequest.status == Status.SUCCESS && requests.isNotEmpty()) {
                AddPeopleRequestsContent(modifier = Modifier.height(512.dp), requests = requests)
            } else {
                //show error and exit?
            }
        }
    }
}

@Composable
fun AddPeopleEmptyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        AddPeopleTitleText(modifier = Modifier.align(Alignment.CenterHorizontally))

        SearchView()

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 39.5.dp, end = 39.5.dp, top = 111.dp),
            text = stringResource(id = R.string.invite_people),
            color = SettingColor,
            fontSize = 20.sp
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 39.5.dp, end = 39.5.dp, top = 16.dp),
            text = stringResource(id = R.string.invite_people_desc),
            color = SettingValueColor,
            textAlign = TextAlign.Center,
            fontSize = 15.sp
        )
    }
}

@Composable
fun AddPeopleRequestsContent(modifier: Modifier = Modifier, requests: List<JoinRequestEntity>) {
    Column(modifier = modifier) {
        AddPeopleTitleText(modifier = Modifier.align(Alignment.CenterHorizontally))

        AddPeopleDescText(modifier = Modifier.align(Alignment.CenterHorizontally))

        SearchView()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 4f
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        CommentETColor,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                }
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                text = stringResource(id = R.string.all_requests),
                color = SettingValueColor,
                fontSize = 16.sp
            )
        }

        LazyColumn {
            items(requests) { item ->
                RequestItem(joinRequestEntity = item)
            }
        }
    }
}

@Composable
fun RequestItem(joinRequestEntity: JoinRequestEntity) {
    ConstraintLayout(modifier = Modifier
        .padding(vertical = 4.dp)
        .fillMaxWidth()) {
        val (profileImage, info, actions) = createRefs()
        Image(
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Image",
            modifier = Modifier
                .clip(CircleShape)
                .constrainAs(profileImage) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    height = Dimension.value(56.dp)
                    width = Dimension.value(56.dp)
                }
        )

        Column(modifier = Modifier.constrainAs(info) {
            top.linkTo(parent.top)
            start.linkTo(profileImage.end, margin = 16.dp)
            bottom.linkTo(parent.bottom)
            end.linkTo(actions.start)
            width = Dimension.fillToConstraints
        }) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = joinRequestEntity.name,
                color = SettingColor,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.padding(top = 5.dp, end = 8.dp),
                text = joinRequestEntity.followers.parseCount(),
                color = SettingValueColor,
                fontSize = 13.sp
            )

            Text(
                modifier = Modifier.padding(top = 5.dp, end = 8.dp),
                text = joinRequestEntity.subTitle,
                color = SettingValueColor,
                fontSize = 13.sp,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(modifier = Modifier
            .fillMaxHeight()
            .constrainAs(actions) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }) {
            Button(
                modifier = Modifier.align(Alignment.CenterVertically),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = SettingColor,
                    contentColor = Color.Black
                ),
                onClick = {
                //tell to accept
            }) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = stringResource(id = R.string.all_accept),
                )
            }

            Image(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        //tell to deny
                    },
                painter = painterResource(id = R.drawable.ic_decline_request),
                contentDescription = "Decline Request"
            )
        }
    }
}

@Composable
fun AddPeopleTitleText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .padding(top = 16.dp),
        text = stringResource(id = R.string.add_people),
        color = SettingColor,
        fontSize = 20.sp
    )
}


@Composable
fun AddPeopleDescText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .padding(start = 26.dp, end = 26.dp, top = 16.dp),
        text = stringResource(id = R.string.add_people_desc),
        color = SettingValueColor,
        fontSize = 15.sp
    )
}

@Composable
fun SearchView(modifier: Modifier = Modifier) {
    val searchState = remember {
        mutableStateOf(TextFieldValue())
    }

    TextField(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        value = searchState.value, onValueChange = { searchState.value = it },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = CommentETColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = SettingValueColor
        ),
        shape = RoundedCornerShape(16.dp),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_users),
                color = SettingValueColor
            )
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search"
            )
        },
        singleLine = true,
        textStyle = TextStyle(color = SettingValueColor)
    )
}

@Preview(showBackground = true)
@Composable
fun AddPeopleBottomSheetEmptyPreview() {
    AddPeopleEmptyContent()
}

@Preview(showBackground = true)
@Composable
fun AddPeopleBottomSheetRequestsPreview() {
    AddPeopleRequestsContent(requests = MockData.getJoinRequests())
}
