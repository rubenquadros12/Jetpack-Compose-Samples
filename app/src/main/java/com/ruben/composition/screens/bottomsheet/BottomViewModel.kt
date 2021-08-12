package com.ruben.composition.screens.bottomsheet

import androidx.lifecycle.ViewModel
import com.ruben.composition.data.MockData
import com.ruben.composition.model.LiveStreamSettingsEntity
import com.ruben.composition.model.SettingValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by Ruben Quadros on 12/08/21
 **/
@HiltViewModel
class BottomViewModel: ViewModel() {

    private var _settingsData: MutableStateFlow<List<LiveStreamSettingsEntity>> =
        MutableStateFlow(MockData.getLiveStreamSettings())
    private val settingsData = _settingsData.asStateFlow()

    private val _uiState: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState.InitialState)
    val uiState = _uiState.asStateFlow()

    fun getSettings() = settingsData

    fun handleSettingsChange() {

    }

    fun handlePaidPromoSettingsChange(settingValue: SettingValue) {
        _settingsData.value[3].value = settingValue
    }

    fun handleSettingsClick(index: Int) {
        if (index == 2) {
            _uiState.value = SettingsState.NavigateToFilters
        } else if (index == 3) {
            _uiState.value = SettingsState.NavigateToPaidPromo
        }
    }

}