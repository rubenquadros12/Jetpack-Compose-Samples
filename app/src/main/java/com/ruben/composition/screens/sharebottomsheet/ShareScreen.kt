package com.ruben.composition.screens.sharebottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.ruben.composition.R
import com.ruben.composition.components.BackButtonAppBar
import com.ruben.composition.share.IconInfo
import com.ruben.composition.share.PackageInfoUtil
import com.ruben.composition.ui.theme.CommentETColor
import com.ruben.composition.ui.theme.Pink
import com.ruben.composition.ui.theme.SettingColor
import com.ruben.composition.ui.theme.SettingValueColor
import kotlinx.coroutines.launch

/**
 * Created by Ruben Quadros on 20/08/21
 **/
@ExperimentalMaterialApi
@Composable
fun ShareScreen(navigateBack: () -> Unit) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val isCreator = remember {
        mutableStateOf(false)
    }

    fun onClose() {
        coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    Surface(color = Color.Black) {
        BottomSheetScaffold(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding(),
            sheetContent = { if (isCreator.value) ShareBottomSheetCreator(onClose = { onClose() }) else ShareBottomSheetUser() },
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
                            isCreator.value = true
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
                        Text(text = stringResource(id = R.string.share_creator))
                    }

                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            isCreator.value = false
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
                        Text(text = stringResource(id = R.string.share_user))
                    }
                }
            }
        )
    }
}

@Composable
fun ShareBottomSheetCreator(onClose: () -> Unit) {
    Column {
        ShareList()

        Text(
            text = stringResource(id = R.string.all_close),
            color = SettingValueColor,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onClose.invoke()
                }
        )
    }
}

@Composable
fun ShareList() {
    val context = LocalContext.current
    Column {
        LazyRow(
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp)
                .fillMaxWidth()
        ) {
            items(PackageInfoUtil.getIconList(true, context)) { item ->
                ShareItem(item)
            }
        }

        Divider(
            color = CommentETColor,
            thickness = 3.dp,
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun ShareBottomSheetUser() {
    Column {
        ShareList()
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(CommentETColor)) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (settingImage, setting, value, image) = createRefs()
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.ic_active_false), contentDescription = "Video",
                    modifier = Modifier.constrainAs(settingImage) {
                        top.linkTo(parent.top, margin = 8.75.dp)
                        bottom.linkTo(parent.bottom, 8.75.dp)
                        start.linkTo(parent.start, 16.75.dp)
                    }
                )
                Text(
                    text = stringResource(id = R.string.video_quality),
                    color = SettingColor,
                    fontSize = 15.sp,
                    modifier = Modifier.constrainAs(setting) {
                        top.linkTo(parent.top, margin = 11.dp)
                        bottom.linkTo(parent.bottom, margin = 11.dp)
                        start.linkTo(settingImage.end, margin = 16.75.dp)
                    }
                )
                Text(
                    text = stringResource(id = R.string.vide_quality_value),
                    color = SettingValueColor,
                    fontSize = 13.sp,
                    modifier = Modifier.constrainAs(value) {
                        top.linkTo(parent.top, margin = 12.dp)
                        bottom.linkTo(parent.bottom, margin = 12.dp)
                        end.linkTo(image.start, margin = 8.dp)
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_right_arrow), contentDescription = "Arrow",
                    modifier = Modifier.constrainAs(image) {
                        top.linkTo(parent.top, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                )
            }
        }
        Box(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(CommentETColor)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        modifier = Modifier
                            .padding(vertical = 8.75.dp, horizontal = 16.75.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.ic_active_false),
                        contentDescription = "Report",
                        colorFilter = ColorFilter.tint(Pink)
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 11.dp).align(Alignment.CenterVertically),
                        text = stringResource(id = R.string.report_stream),
                        color = Pink,
                        fontSize = 15.sp
                    )
                }
        }
    }
}

@Composable
fun ShareItem(iconInfo: IconInfo) {

    iconInfo.drawableResource?.let {
        Image(
            bitmap = it.toBitmap().asImageBitmap(),
            contentDescription = "Share",
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(40.dp)
                .clip(CircleShape)
        )
    }

    iconInfo.drawableResourceString?.let {
        Image(
            painter = painterResource(id = it),
            contentDescription = "Share",
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(40.dp)
                .clip(CircleShape)
        )
    }

}