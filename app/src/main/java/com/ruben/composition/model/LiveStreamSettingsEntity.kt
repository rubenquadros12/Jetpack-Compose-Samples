package com.ruben.composition.model

/**
 * Created by Ruben Quadros on 11/08/21
 **/
data class LiveStreamSettingsEntity(
    val name: String,
    val value: SettingValue,
    val type: SettingType
)