package com.ruben.composition.data

import com.ruben.composition.model.LiveStreamerEntity

/**
 * Created by Ruben Quadros on 10/08/21
 **/
object MockLiveStreamersData {

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

}