package com.ruben.composition.screens.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.composition.data.MockData
import com.ruben.composition.model.LiveStreamSettingsEntity
import com.ruben.composition.model.SettingValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ruben Quadros on 12/08/21
 **/
@HiltViewModel
class BottomViewModel @Inject constructor(): ViewModel() {

    private var _settingsData: MutableStateFlow<List<LiveStreamSettingsEntity>> =
        MutableStateFlow(MockData.getLiveStreamSettings())
    private val settingsData = _settingsData.asStateFlow()

    private val _uiSideEffect = Channel<SettingsSideEffect>(Channel.BUFFERED)
    val uiSideEffect: Flow<SettingsSideEffect> = _uiSideEffect.receiveAsFlow()

    fun getSettings() = settingsData

    fun handleSettingsChange() {

    }

    fun handlePaidPromoSettingsChange(settingValue: SettingValue) {
        _settingsData.value[3].value = settingValue
    }

    fun handleSettingsClick(index: Int) {
        if (index == 2) {
             viewModelScope.launch {
                 _uiSideEffect.send(SettingsSideEffect.NavigateToFilters)
             }
        } else if (index == 3) {
            viewModelScope.launch {
                _uiSideEffect.send(SettingsSideEffect.NavigateToPaidPromo)
            }
        }
    }

}