package com.ruben.composition.screens.bottomsheet

/**
 * Created by Ruben Quadros on 13/08/21
 **/
sealed class SettingsSideEffect {
    object NavigateToFilters: SettingsSideEffect()
    object NavigateToPaidPromo: SettingsSideEffect()
}
