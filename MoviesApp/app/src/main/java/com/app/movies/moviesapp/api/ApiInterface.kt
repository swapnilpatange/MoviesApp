package com.app.movies.moviesapp.api

import com.app.movies.moviesapp.model.PageDetail
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("getMoviesData")
    suspend fun getMoviesData( @Body pageDetail: PageDetail): Response<JsonElement>
}