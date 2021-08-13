package com.ruben.composition.screens.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ruben.composition.R
import com.ruben.composition.components.MojAppBar
import com.ruben.composition.components.SettingItem
import com.ruben.composition.model.LiveStreamSettingsEntity
import com.ruben.composition.model.SettingType
import com.ruben.composition.model.SettingValue
import com.ruben.composition.ui.theme.SettingValueColor

/**
 * Created by Ruben Quadros on 12/08/21
 **/
@Composable
fun PaidPromoScreen(
    navigateBack: () -> Unit,
    bottomViewModel: BottomViewModel
) {
    Scaffold(
        topBar = { MojAppBar(
            title = stringResource(id = R.string.paid_promo_title),
            navigateBack = navigateBack)
        },
        content = {
            Column(modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = R.string.paid_promo_desc),
                    color = SettingValueColor
                )

                SettingItem(
                    bottomViewModel = bottomViewModel,
                    index = 3,
                    liveStreamSettingsEntity = LiveStreamSettingsEntity(
                        stringResource(id = R.string.paid_promo_title),
                        SettingValue.ON,
                        SettingType.SWITCH
                    )
                )
            }
        }
    )
}