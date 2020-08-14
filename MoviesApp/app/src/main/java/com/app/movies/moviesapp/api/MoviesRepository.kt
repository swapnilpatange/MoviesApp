package com.app.movies.moviesapp.api

import com.app.movies.moviesapp.api.RetrofitService
import com.app.movies.moviesapp.model.ApiResponse
import com.app.movies.moviesapp.model.PageDetail

class MoviesRepository {
    val retrofitService = RetrofitService()
    var apiResponse: ApiResponse? = null
    suspend fun getMoviesData(pageNumber: String): ApiResponse? {
        var pageDetail = PageDetail(pageNumber)
        val mapiResponse = retrofitService.getMoviesData(pageDetail)
        if (apiResponse == null) {
            apiResponse = mapiResponse
        } else {
            apiResponse?.page?.content_items?.content?.addAll(mapiResponse?.page?.content_items?.content!!)
        }
        if (apiResponse?.page?.content_items?.content!!.size >= mapiResponse?.page?.total_content_items!!.toInt())
            apiResponse?.loadmore = false
        else
            apiResponse?.loadmore = true
        return apiResponse
    }
}