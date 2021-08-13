package com.ruben.composition.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ruben.composition.R
import com.ruben.composition.model.LiveStreamSettingsEntity
import com.ruben.composition.model.SettingType
import com.ruben.composition.model.SettingValue
import com.ruben.composition.screens.bottomsheet.BottomViewModel
import com.ruben.composition.ui.theme.SettingColor
import com.ruben.composition.ui.theme.SettingRowColor
import com.ruben.composition.ui.theme.SettingSwitchColor
import com.ruben.composition.ui.theme.SettingValueColor

/**
 * Created by Ruben Quadros on 12/08/21
 **/
@Composable
fun SettingItem(
    bottomViewModel: BottomViewModel,
    index: Int,
    liveStreamSettingsEntity: LiveStreamSettingsEntity
) {
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
                    when (index) {
                        0 -> {
                            //this is live req
                        }
                        1 -> {
                            //this is comments
                        }
                        2 -> {
                        }
                        3 -> {
                            //this is paid promo
                            bottomViewModel.handlePaidPromoSettingsChange(
                                if (it) SettingValue.ON else SettingValue.OFF
                            )
                        }
                        else -> {
                            //do nothing
                        }
                    }
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
                    .clickable {
                        bottomViewModel.handleSettingsClick(index)
                    }
                    .constrainAs(arrow) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}