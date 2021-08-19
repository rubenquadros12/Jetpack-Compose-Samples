package com.ruben.composition.model

/**
 * Created by Ruben Quadros on 18/08/21
 **/
data class JoinRequestEntity(
    val userId: Int,
    val name: String,
    val followers: Long,
    val subTitle: String,
    val profileImage: String
)