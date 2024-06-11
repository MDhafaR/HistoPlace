package org.d3if3068.assesment3.histoplace.model

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if3068.assesment3.histoplace.network.ApiStatus
import org.d3if3068.assesment3.histoplace.network.TempatApi
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Tempat>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
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

    fun saveData(
        userId: String,
        namaTempat: String,
        biayaMasuk: String,
        kota: String,
        negara: String,
        alamat: String,
        rating: Int,
        catatan: String,
        bitmap: Bitmap
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = TempatApi.service.postTempat(
                    userId,
                    namaTempat.toRequestBody("text/plain".toMediaTypeOrNull()),
                    biayaMasuk.toRequestBody("text/plain".toMediaTypeOrNull()),
                    kota.toRequestBody("text/plain".toMediaTypeOrNull()),
                    negara.toRequestBody("text/plain".toMediaTypeOrNull()),
                    alamat.toRequestBody("text/plain".toMediaTypeOrNull()),
                    rating.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    catatan.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )

                if (result.status == "success")
                    retrieveData()
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure2: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }


    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData(
            "image", "image.jpg", requestBody)
    }

    fun clearMessage() { errorMessage.value = null }
}