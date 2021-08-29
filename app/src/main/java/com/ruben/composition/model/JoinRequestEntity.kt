package com.ruben.composition.model

/**
 * Created by Ruben Quadros on 18/08/21
 **/
data class JoinRequestEntity(
    val memberId: Int,
    val memberName: String,
    val numberOfFollwers: Long,
    val memberHandle: String,
    val memberThumb: String
)