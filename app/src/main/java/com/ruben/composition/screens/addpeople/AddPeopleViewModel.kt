package com.ruben.composition.screens.addpeople

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.composition.data.MockData
import com.ruben.composition.model.JoinRequestEntity
import com.ruben.composition.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections.addAll
import javax.inject.Inject

/**
 * Created by Ruben Quadros on 18/08/21
 **/
@HiltViewModel
class AddPeopleViewModel @Inject constructor(): ViewModel() {

    private val _uiState: MutableStateFlow<AddPeopleState> = MutableStateFlow(AddPeopleState.InitialState)
    val uiState = _uiState.asStateFlow()

    fun getEmptyList() {
        viewModelScope.launch {
            _uiState.value = AddPeopleState.InitialState
            delay(2000)
            _uiState.value = AddPeopleState.JoinRequest(status = Status.SUCCESS, requests = arrayListOf<JoinRequestEntity>().toSnapshot())
        }
    }

    fun getJoinRequests() {
        _uiState.value = AddPeopleState.InitialState
        _uiState.value = AddPeopleState.JoinRequest(status = Status.SUCCESS, requests = MockData.getJoinRequests().toSnapshot())
    }

}

private fun <E> List<E>.toSnapshot(): SnapshotStateList<E> = SnapshotStateList<E>().also {
    it.addAll(this)
}
