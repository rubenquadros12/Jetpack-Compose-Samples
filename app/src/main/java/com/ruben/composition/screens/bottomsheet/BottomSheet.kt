package com.ruben.composition.screens.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ruben.composition.R
import com.ruben.composition.components.BackButtonAppBar
import com.ruben.composition.components.SettingItem
import com.ruben.composition.model.LiveStreamSettingsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Ruben Quadros on 11/08/21
 **/
@ExperimentalMaterialApi
@Composable
fun BottomSheetScreen(
    navigateBack: () -> Unit,
    navigateToPaidPromo: () -> Unit,
    navigateToFilters: () -> Unit,
    bottomViewModel: BottomViewModel
) {

    HandleSideEffects(bottomViewModel.uiSideEffect, navigateToPaidPromo, navigateToFilters)

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val mockSettings = bottomViewModel.getSettings().collectAsState()

    BottomSheetScaffold(
        sheetContent = { BottomSheet(bottomViewModel, mockSettings.value) },
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
                    text = stringResource(id = R.string.live_requests_setting_value, mockSettings.value[0].value),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.comments_setting_value, mockSettings.value[1].value),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.comment_filter_setting_value, mockSettings.value[2].value),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.paid_promo_setting_value, mockSettings.value[3].value),
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

@Composable
fun BottomSheet(bottomViewModel: BottomViewModel, settings: List<LiveStreamSettingsEntity>) {
    LazyColumn {
        itemsIndexed(settings) { index, setting ->
            SettingItem(bottomViewModel = bottomViewModel, index = index, liveStreamSettingsEntity = setting)
        }
    }
}

@Composable
fun HandleSideEffects(uiSideEffectFlow: Flow<SettingsSideEffect>, navigateToPaidPromo: () -> Unit, navigateToFilters: () -> Unit) {
    LaunchedEffect(uiSideEffectFlow) {
        uiSideEffectFlow.collect { uiSideEffect ->
            when (uiSideEffect) {
                is SettingsSideEffect.NavigateToPaidPromo -> {
                    navigateToPaidPromo.invoke()
                }
                is SettingsSideEffect.NavigateToFilters -> {
                    navigateToFilters.invoke()
                }
            }
        }
    }
}
