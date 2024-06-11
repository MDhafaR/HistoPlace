package org.d3if3068.assesment3.histoplace.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.d3if3068.assesment3.histoplace.model.OpStatus
import org.d3if3068.assesment3.histoplace.model.Tempat
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.lang.reflect.Type

private const val BASE_URL = "https://ghastly-delicate-dragon.ngrok-free.app/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    .baseUrl(BASE_URL)
    .build()

interface TempatApiService {
    @GET("link/files/dhafa/Tempat.php")
    suspend fun getTempat(): List<Tempat>

    @Multipart
    @POST("link/files/dhafa/AddData.php")
    suspend fun postTempat(
        @Header("Authorization") userId: String,
        @Part("namaTempat") namaTempat: RequestBody,
        @Part("biayaMasuk") biayaMasuk: RequestBody,
        @Part("kota") kota: RequestBody,
        @Part("negara") negara: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("rating") rating: RequestBody,
        @Part("catatan") catatan: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

}

object TempatApi {
    val service: TempatApiService by lazy {
        retrofit.create(TempatApiService::class.java)
    }

    fun getTempatUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }