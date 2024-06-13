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

    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                val result = TempatApi.service.getTempat(userId)
                Log.d("MainViewModel", "Succsess: $result")

                data.value = TempatApi.service.getTempat(userId)
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failur: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun saveData(
        userId: String,
        bitmap: Bitmap,
        namaTempat: String,
        biayaMasuk: String,
        kota: String,
        negara: String,
        rating: Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = TempatApi.service.postTempat(
                    userId,
                    bitmap.toMultipartBody(),
                    namaTempat.toRequestBody("text/plain".toMediaTypeOrNull()),
                    biayaMasuk.toRequestBody("text/plain".toMediaTypeOrNull()),
                    kota.toRequestBody("text/plain".toMediaTypeOrNull()),
                    negara.toRequestBody("text/plain".toMediaTypeOrNull()),
                    rating.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                )

                if (result.status == "success")
                    retrieveData(userId)
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure2: ${e.message}")
                errorMessage.value = "${e.message}"
            }
        }
    }

    fun deleteData(userId: String, hewanId: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = TempatApi.service.deleteData(userId, hewanId)
                if (response.status == "success") {
                    Log.d("MainViewModel", "Image deleted successfully: $hewanId")
                    retrieveData(userId) // Refresh data after deletion
                } else {
                    Log.d("MainViewModel", "Failed to delete the image: ${response.message}")
                    errorMessage.value = "Failed to delete the image: ${response.message}"
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
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
            "image_id", "image.jpg", requestBody)
    }

    fun clearMessage() { errorMessage.value = null }
}