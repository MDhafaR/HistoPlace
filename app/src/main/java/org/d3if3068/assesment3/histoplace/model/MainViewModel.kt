package org.d3if3068.assesment3.histoplace.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3if3068.assesment3.histoplace.network.ApiStatus
import org.d3if3068.assesment3.histoplace.network.TempatApi

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Tempat>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    init {
        retrieveData()
    }

    fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                val result = TempatApi.service.getTempat()
                Log.d("MainViewModel", "Succsess: $result")

                data.value = TempatApi.service.getTempat()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failur: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }
}