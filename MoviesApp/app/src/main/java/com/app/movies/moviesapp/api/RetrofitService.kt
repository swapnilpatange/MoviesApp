package com.app.movies.moviesapp.api

import android.util.Log
import com.app.movies.moviesapp.model.ApiResponse
import com.app.movies.moviesapp.model.PageDetail
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {


    companion object Factory {
        var gson = GsonBuilder().setLenient().create()
        fun create(): ApiInterface {
            Log.e("retrofit", "create")
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://swapnilpatange-001-site1.etempurl.com/dietservice/service.svc/")
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

    suspend fun getMoviesData(pageDetail: PageDetail): ApiResponse? {
        val retrofitCall = create().getMoviesData(pageDetail)
        if (retrofitCall.isSuccessful) {
            var gson = Gson()
            var apiResponse: ApiResponse =
                gson.fromJson<ApiResponse>(retrofitCall.body()?.asString, ApiResponse::class.java)
            return apiResponse

        }
        return null
    }

}