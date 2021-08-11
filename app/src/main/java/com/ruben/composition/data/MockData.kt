package com.ruben.composition.data

import com.ruben.composition.model.LiveStreamSettingsEntity
import com.ruben.composition.model.LiveStreamerEntity
import com.ruben.composition.model.SettingType
import com.ruben.composition.model.SettingValue

/**
 * Created by Ruben Quadros on 10/08/21
 **/
object MockData {

    fun getMockLiveStreamers(): List<LiveStreamerEntity> {
        val liveStreamers = arrayListOf<LiveStreamerEntity>()
        liveStreamers.add(LiveStreamerEntity("@rubenquadros", "https://ruben.jpg"))
        liveStreamers.add(LiveStreamerEntity("@dilrajsingh", "https://dilraj.jpg"))
        liveStreamers.add(LiveStreamerEntity("@ajaymeher", "https://ajay.jpg"))
        liveStreamers.add(LiveStreamerEntity("@deblinahait", "https://deblina.jpg"))
        liveStreamers.add(LiveStreamerEntity("@anirudhmenon", "https://anirudh.jpg"))
        liveStreamers.add(LiveStreamerEntity("@vaibhavbhandula", "https://vaibhav.jpg"))
        liveStreamers.add(LiveStreamerEntity("@nirmalprasad", "https://nirmal.jpg"))
        liveStreamers.add(LiveStreamerEntity("@rohinic", "https://rohini.jpg"))
        liveStreamers.add(LiveStreamerEntity("@yash", "https://yash.jpg"))
        liveStreamers.add(LiveStreamerEntity("@sohil", "https://sohil.jpg"))
        return liveStreamers
    }

    fun getLiveStreamSettings(): List<LiveStreamSettingsEntity> {
        val settings = arrayListOf<LiveStreamSettingsEntity>()
        settings.add(LiveStreamSettingsEntity("Live Requests", SettingValue.ON, SettingType.SWITCH))
        settings.add(LiveStreamSettingsEntity("Comments", SettingValue.ON, SettingType.SWITCH))
        settings.add(LiveStreamSettingsEntity("Comments Filter", SettingValue.ON, SettingType.NORMAL))
        settings.add(LiveStreamSettingsEntity("Paid Promotion", SettingValue.ON, SettingType.NORMAL))
        return settings
    }

}