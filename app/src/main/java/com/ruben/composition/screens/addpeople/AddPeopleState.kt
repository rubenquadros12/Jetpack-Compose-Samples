package com.ruben.composition.screens.addpeople

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.ruben.composition.model.JoinRequestEntity
import com.ruben.composition.model.Status

/**
 * Created by Ruben Quadros on 18/08/21
 **/
sealed class AddPeopleState {
    object InitialState: AddPeopleState()
    data class JoinRequest(val status: Status, val requests: SnapshotStateList<JoinRequestEntity>): AddPeopleState()
}
