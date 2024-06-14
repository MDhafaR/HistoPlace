package org.d3if3068.assesment3.histoplace.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.d3if3068.assesment3.histoplace.model.OpStatus
import org.d3if3068.assesment3.histoplace.model.Tempat
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.lang.reflect.Type

private const val BASE_URL = "https://fd70-103-233-100-227.ngrok-free.app/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    .baseUrl(BASE_URL)
    .build()

interface TempatApiService {
    @GET("belajarRestApiWeb/files/dhafa/Tempat.php")
    suspend fun getTempat(
        @Header("Authorization") userId: String
    ): List<Tempat>

    @GET("belajarRestApiWeb/files/dhafa/TempatSearch.php")
    suspend fun searchBooksByTitle(@Query("search") searchQuery: String): List<Tempat>

    @Multipart
    @POST("belajarRestApiWeb/files/dhafa/AddData.php")
    suspend fun postTempat(
        @Header("Authorization") userId: String,
        @Part image_id: MultipartBody.Part,
        @Part("nama_tempat") nama_tempat: RequestBody,
        @Part("biaya_masuk") biaya_masuk: RequestBody,
        @Part("kota") kota: RequestBody,
        @Part("negara") negara: RequestBody,
        @Part("rating") rating: RequestBody
    ): OpStatus

    @DELETE("belajarRestApiWeb/files/dhafa/DeleteData.php")
    suspend fun deleteData(
        @Header("Authorization") userId: String,
        @Query("id") id: String
    ) : OpStatus
}

object TempatApi {
    val service: TempatApiService by lazy {
        retrofit.create(TempatApiService::class.java)
    }

    fun getTempatUrl(imageId: String): String {
        val encodedImageId = imageId.replace("&", "%26")
        return "${BASE_URL}belajarRestApiWeb/files/dhafa/image.php?image_id=$encodedImageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }