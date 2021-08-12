package com.ruben.composition.screens.bottomsheet

/**
 * Created by Ruben Quadros on 12/08/21
 **/
sealed class SettingsState {
    object InitialState: SettingsState()
    object NavigateToFilters: SettingsState()
    object NavigateToPaidPromo: SettingsState()
}
