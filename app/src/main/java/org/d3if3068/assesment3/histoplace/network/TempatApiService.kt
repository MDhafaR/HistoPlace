package org.d3if3068.assesment3.histoplace.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3068.assesment3.histoplace.model.OpStatus
import org.d3if3068.assesment3.histoplace.model.Photos
import org.d3if3068.assesment3.histoplace.model.Tempat
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://d3if3068.my.id/files/resting/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    .baseUrl(BASE_URL)
    .build()

interface TempatApiService {
    @GET("Tempat.php")
    suspend fun getTempat(
        @Header("Authorization") userId: String
    ): List<Tempat>

    @GET("TempatSearch.php")
    suspend fun searchBooksByTitle(@Query("search") searchQuery: String): List<Tempat>

    @Multipart
    @POST("AddData.php")
    suspend fun postTempat(
        @Header("Authorization") userId: String,
        @Part image_id: MultipartBody.Part,
        @Part("nama_tempat") nama_tempat: RequestBody,
        @Part("biaya_masuk") biaya_masuk: RequestBody,
        @Part("kota") kota: RequestBody,
        @Part("negara") negara: RequestBody,
        @Part("rating") rating: RequestBody
    ): OpStatus

    @GET("photos.php")
    suspend fun getPhots(
        @Query("tempat_id") id: String,
    ): List<Photos>

    @Multipart
    @POST("AddPhotos.php")
    suspend fun postPhotos(
        @Part photoUrl: MultipartBody.Part,
        @Part("tempat_id") tempat_id: RequestBody
    ): Response<OpStatus>

    @Multipart
    @POST("Detail.php")
    suspend fun updateDetail(
        @Part("alamat") alamat: RequestBody,
        @Part("catatan") catatan: RequestBody,
        @Part("id") id: RequestBody,
        @Header("Authorization") userId: String
    ): OpStatus

    @DELETE("DeleteData.php")
    suspend fun deleteData(
        @Header("Authorization") userId: String,
        @Query("id") id: String
    ) : OpStatus

    @DELETE("DeletePhoto.php")
    suspend fun deletePhoto(
        @Query("id") id: String
    ) : OpStatus
}

object TempatApi {
    val service: TempatApiService by lazy {
        retrofit.create(TempatApiService::class.java)
    }

    fun getTempatUrl(imageId: String): String {
        val encodedImageId = imageId.replace("&", "%26")
        return "${BASE_URL}image.php?image_id=$encodedImageId"
    }

    fun getPhotosUrl(imageId: String): String {
        val encodedImageId = imageId.replace("&", "%26")
        return "${BASE_URL}imagePhotos.php?photoUrl=$encodedImageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }