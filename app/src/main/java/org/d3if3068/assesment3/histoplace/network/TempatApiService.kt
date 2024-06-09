package org.d3if3068.assesment3.histoplace.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3068.assesment3.histoplace.model.Tempat
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://histoplace-e5581-default-rtdb.firebaseio.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TempatApiService {
    @GET("places.json")
    suspend fun getTempat(): List<Tempat>
}

object TempatApi {
    val service: TempatApiService by lazy {
        retrofit.create(TempatApiService::class.java)
    }

    fun getTempatUrl(imageId: String): String {
        return "$BASE_URL$imageId.jpg"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }