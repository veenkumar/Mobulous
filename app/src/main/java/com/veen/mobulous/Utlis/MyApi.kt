package com.veen.mobulous.Utlis

import com.veen.mobulous.model.SearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    companion object {
        private val url = "https://developers.zomato.com/api/v2.1/"
        operator fun invoke(): MyApi {
            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(url)
                    .build()
                    .create(MyApi::class.java)
        }
    }

    @Headers("Content-Type: application/json", "user-key: 41a5401d2f4bb7aba885fe8d6d1f3d69")
    @GET("search?")
    suspend fun getdata(): Response<SearchResponse>

//    @Headers("Content-Type: application/json", "user-key: 41a5401d2f4bb7aba885fe8d6d1f3d69")
//    @GET("search?q=")
//    fun searchdata(
//            @QueryMap params: Map<String, String>?
//    ): Call<SearchResponse>

}
/*
@GET("/place/autocomplete/json")
void getDetails(
             @Query("input") String input,
             @Query("location") String location,
             @Query("key") String key,
             Callback<Response> callback);

    @GET("api/videomarker/user/video/{user_id}/{video_id}")
        Call<MarkerParams> getMarkerData(@Path("user_id") String userid);


 */