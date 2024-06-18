package org.d3if3068.assesment3.histoplace.model

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if3068.assesment3.histoplace.network.ApiStatus
import org.d3if3068.assesment3.histoplace.network.TempatApi
import retrofit2.HttpException
import retrofit2.Response
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Tempat>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var dataPhotos = mutableStateOf(emptyList<Photos>())
        private set

    var statusPhotos = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    var searchData = mutableStateOf(emptyList<Tempat>())
        private set

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

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

    fun retrievePhotos(tempatId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            statusPhotos.value = ApiStatus.LOADING
            try {
                val result = TempatApi.service.getPhots(tempatId)
                Log.d("MainViewModel", "Success: $result")

                dataPhotos.value = TempatApi.service.getPhots(tempatId)
                statusPhotos.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                statusPhotos.value = ApiStatus.FAILED
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

    fun savePhotos(bitmap: Bitmap, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bitmapPart = bitmap.toPhotos()
                val idPart = id.toRequestBody("text/plain".toMediaTypeOrNull())

                Log.d("MainViewModel", "Starting upload for id: $id")

                val response: Response<OpStatus> = TempatApi.service.postPhotos(bitmapPart, idPart)

                Log.d("MainViewModel", "Received response with code: ${response.code()}")

                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("MainViewModel", "Response body: $result")
                    if (result?.status == "success") {
                        Log.d("MainViewModel", "Upload successful")
                        retrievePhotos(id)
                    } else {
                        val errorMessage = result?.message ?: "Terjadi kesalahan saat mengunggah file"
                        Log.e("MainViewModel", "Upload failed with message: $errorMessage")
                        throw Exception(errorMessage)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("MainViewModel", "Error response body: $errorBody")
                    throw Exception(errorBody ?: "Terjadi kesalahan saat mengunggah file")
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("MainViewModel", "HttpException: $errorBody")
                errorMessage.value = errorBody ?: "Unknown error"
            } catch (e: IOException) {
                Log.e("MainViewModel", "Network Failure: ${e.message}")
                errorMessage.value = "Network Failure: ${e.message}"
            } catch (e: Exception) {
                Log.e("MainViewModel", "General Exception: ${e.message}")
                errorMessage.value = e.message ?: "Unknown error"
            }
        }
    }



    fun saveDetail(
        alamat: String,
        catatan: String,
        id: String,
        userId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = TempatApi.service.updateDetail(
                    alamat.toRequestBody("text/plain".toMediaTypeOrNull()),
                    catatan.toRequestBody("text/plain".toMediaTypeOrNull()),
                    id.toRequestBody("text/plain".toMediaTypeOrNull()),
                    userId
                )

                if (result.status == "success") {
                    retrieveData(userId)
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure3: ${e.message}")
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

    fun deletePhoto(id: String, tempatId: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = TempatApi.service.deletePhoto(id)
                if (response.status == "success") {
                    Log.d("MainViewModel", "Image deleted successfully: $id")
                    retrievePhotos(tempatId)
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

    fun searchBooksByTitle(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = TempatApi.service.searchBooksByTitle(query)
                searchData.value = result
            } catch (e: Exception) {
                Log.d("MainViewModel", "Search Failure: ${e.message}")
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        searchBooksByTitle(query)
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

    private fun Bitmap.toPhotos(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData(
            "photoUrl", "image.jpg", requestBody)
    }


    fun clearMessage() { errorMessage.value = null }
}