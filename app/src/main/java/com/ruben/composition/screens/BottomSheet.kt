package com.ruben.composition.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ruben.composition.R
import com.ruben.composition.components.BackButtonAppBar
import com.ruben.composition.data.MockData
import com.ruben.composition.model.LiveStreamSettingsEntity
import com.ruben.composition.model.SettingType
import com.ruben.composition.model.SettingValue
import com.ruben.composition.showToast
import com.ruben.composition.ui.theme.SettingColor
import com.ruben.composition.ui.theme.SettingRowColor
import com.ruben.composition.ui.theme.SettingSwitchColor
import com.ruben.composition.ui.theme.SettingValueColor
import kotlinx.coroutines.launch

/**
 * Created by Ruben Quadros on 11/08/21
 **/
@ExperimentalMaterialApi
@Composable
fun BottomSheetScreen(navigateBack: () -> Unit) {
    val context = LocalContext.current
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val isInitial = remember {
        mutableStateOf(true)
    }

    if (bottomSheetScaffoldState.bottomSheetState.currentValue == BottomSheetValue.Collapsed) {
        //send captured values
        if (isInitial.value.not()) {
            context.showToast(" === Bottom Sheet Closing === ")
        }
        isInitial.value = false
    }

    val mockSettings = MockData.getLiveStreamSettings()

    BottomSheetScaffold(
        sheetContent = { BottomSheet(mockSettings) },
        scaffoldState = bottomSheetScaffoldState,
        topBar = { BackButtonAppBar(title = stringResource(id = R.string.bottom_sheet_title), navigateBack = navigateBack) },
        sheetGesturesEnabled = false,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetBackgroundColor = Color.Black,
        sheetPeekHeight = 0.dp,
        content = {
            Column {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center),
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
                        Text(text = stringResource(id = R.string.show_bottom_sheet))
                    }
                }
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.setting_values),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.live_requests_setting_value, mockSettings[0].value),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.comments_setting_value, mockSettings[1].value),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.comment_filter_setting_value, mockSettings[2].value),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.paid_promo_setting_value, mockSettings[3].value),
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

@Composable
fun BottomSheet(mockSettings: List<LiveStreamSettingsEntity>) {
    LazyColumn {
        itemsIndexed(mockSettings) { index, setting ->
            SettingItem(index = index, liveStreamSettingsEntity = setting)
        }
    }
}

@Composable
fun SettingItem(index: Int, liveStreamSettingsEntity: LiveStreamSettingsEntity) {
    val checkedState = remember {
        mutableStateOf(
            if (liveStreamSettingsEntity.type == SettingType.SWITCH) liveStreamSettingsEntity.value == SettingValue.ON else false
        )
    }
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(SettingRowColor)) {
        val (circle, name, switch, value, arrow) = createRefs()
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .size(24.dp)
                .clip(CircleShape)
                .background(SettingColor)
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = liveStreamSettingsEntity.name,
            color = SettingColor,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(circle.end)
                    bottom.linkTo(parent.bottom)
                    if (liveStreamSettingsEntity.type == SettingType.SWITCH) {
                        end.linkTo(switch.start)
                    } else {
                        end.linkTo(value.start)
                    }
                    width = Dimension.fillToConstraints
                }
        )
        if (liveStreamSettingsEntity.type == SettingType.SWITCH) {
            Switch(
                checked = checkedState.value,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.White,
                    checkedTrackColor = SettingSwitchColor,
                    checkedTrackAlpha = 1f,
                    uncheckedTrackColor = Color.White
                ),
                onCheckedChange = {
                    checkedState.value = it
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .constrainAs(switch) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )
        } else {
            Text(
                text = liveStreamSettingsEntity.value.toString(),
                color = SettingValueColor,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp)
                    .constrainAs(value) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(arrow.start)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "Right Arrow",
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, end = 16.dp)
                    .constrainAs(arrow) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun BottomSheetScreenPreview() {
    BottomSheetScreen {

    }
}

@Preview(showBackground = true)
@Composable
fun SettingItemPreview() {
    SettingItem(liveStreamSettingsEntity = LiveStreamSettingsEntity("Live Requests", SettingValue.ON, SettingType.SWITCH), index = 0)
}
