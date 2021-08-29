package com.ruben.composition.screens.sharebottomsheet

import android.content.Context
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
import androidx.core.app.ShareCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.ruben.composition.R
import com.ruben.composition.components.BackButtonAppBar
import com.ruben.composition.copyToClipBoard
import com.ruben.composition.isPackageInstalled
import com.ruben.composition.share.IconInfo
import com.ruben.composition.share.PackageConstants.ICON_NAME_COPY
import com.ruben.composition.share.PackageConstants.ICON_NAME_FB
import com.ruben.composition.share.PackageConstants.ICON_NAME_INSTA
import com.ruben.composition.share.PackageConstants.ICON_NAME_MORE
import com.ruben.composition.share.PackageConstants.ICON_NAME_TWITTER
import com.ruben.composition.share.PackageConstants.ICON_NAME_WHATSAPP
import com.ruben.composition.share.PackageInfo
import com.ruben.composition.share.PackageInfoUtil
import com.ruben.composition.showToast
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
            sheetContent = { if (isCreator.value) ShareBottomSheetCreator(onClose = { onClose() }) else ShareBottomSheetUser(onClose = { onClose() }) },
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
        ShareList(onClose)

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
fun ShareList(closeDialog: () -> Unit) {
    val context = LocalContext.current
    Column {
        LazyRow(
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp)
                .fillMaxWidth()
        ) {
            items(PackageInfoUtil.getIconList(false, context)) { item ->
                ShareItem(item, closeDialog)
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
fun ShareBottomSheetUser(onClose: () -> Unit) {
    Column {
        ShareList(onClose)
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
fun ShareItem(iconInfo: IconInfo, closeDialog: () -> Unit) {

    val context = LocalContext.current

    iconInfo.drawableResource?.let {
        Image(
            bitmap = it.toBitmap().asImageBitmap(),
            contentDescription = "Share",
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(40.dp)
                .clip(CircleShape)
                .clickable {
                    shareLiveStream(iconInfo = iconInfo, "https://mojapp.in/@ruben/live/livestreamid", context, closeDialog)
                }
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
                .clickable {
                    shareLiveStream(iconInfo = iconInfo, "https://mojapp.in/@ruben/live/livestreamid", context, closeDialog)
                }
        )
    }

}

fun shareLiveStream(iconInfo: IconInfo, livestreamLink: String, context: Context, closeDialog: () -> Unit) {

    fun share(livestreamLink: String, packageInfo: PackageInfo? = null) {
        val text = livestreamLink

        val title = context.getString(R.string.choose_to_share)
        val mimeType = "text/plain"

        val shareIntentBuilder = ShareCompat.IntentBuilder(context)
            .setChooserTitle(title)
            .setType(mimeType)
            .setText(text)

        val shareIntent = if (packageInfo == null || packageInfo == PackageInfo.OTHERS) shareIntentBuilder.createChooserIntent() else
            shareIntentBuilder.intent

        if (packageInfo != null && context.isPackageInstalled(packageInfo.packageName)) {
            shareIntent.setPackage(packageInfo.packageName)
        }

        if (shareIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(shareIntent)
        } else {
            //application is not found
            //handle error
            context.showToast(context.getString(R.string.application_not_found))
        }
    }

    when (iconInfo.iconName) {
        ICON_NAME_WHATSAPP -> share(livestreamLink, PackageInfo.WHATSAPP)
        ICON_NAME_FB -> share(livestreamLink, PackageInfo.FACEBOOK)
        ICON_NAME_TWITTER -> share(livestreamLink, PackageInfo.TWITTER)
        ICON_NAME_INSTA -> share(livestreamLink, PackageInfo.INSTAGRAM)
        ICON_NAME_MORE -> share(livestreamLink)
        ICON_NAME_COPY -> {
            context.copyToClipBoard(livestreamLink)
            closeDialog.invoke()
        }
    }
}