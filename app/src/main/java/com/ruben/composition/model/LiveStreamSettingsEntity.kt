package com.ruben.composition.model

/**
 * Created by Ruben Quadros on 11/08/21
 **/
data class LiveStreamSettingsEntity(
    var name: String,
    var value: SettingValue,
    var type: SettingType
) {
    constructor(): this ("", SettingValue.DEFAULT, SettingType.NORMAL)
}