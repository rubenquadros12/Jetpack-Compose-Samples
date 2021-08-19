package com.ruben.composition.data

import com.ruben.composition.model.JoinRequestEntity
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

    fun getJoinRequests(): List<JoinRequestEntity> {
        val requests = arrayListOf<JoinRequestEntity>()
        requests.add(JoinRequestEntity(1, "Ruben Quadros", 10, "@ruben", "https://ruben.jpg"))
        requests.add(JoinRequestEntity(2, "Dilraj Singh", 2000, "@dilraj", "https://ruben.jpg"))
        requests.add(JoinRequestEntity(3, "Ajay Meher", 300000, "@ajaymeher", "https://ruben.jpg"))
        requests.add(JoinRequestEntity(4, "Nirmal Prasad", 1100000007, "@nirmal", "https://ruben.jpg"))
        requests.add(JoinRequestEntity(5, "Faizan Memon", 300, "@faizan", "https://ruben.jpg"))
        requests.add(JoinRequestEntity(6, "Akshay Agarwal", 400000, "@akshay", "https://ruben.jpg"))
        requests.add(JoinRequestEntity(7, "Yash Jain", 50, "@yash", "https://ruben.jpg"))
        return requests
    }

}