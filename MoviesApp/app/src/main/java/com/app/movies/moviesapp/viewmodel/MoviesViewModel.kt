package com.app.movies.moviesapp.viewmodel

import androidx.lifecycle.*
import com.app.movies.moviesapp.api.MoviesRepository
import com.app.movies.moviesapp.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel : ViewModel {

    @set:Inject
    private var moviesRepository: MoviesRepository? = null
    private var pageNumber: Int = 1;
    private val mutableMoviesLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    private var getMoviesJob: Job? = null

    @Inject
    constructor(moviesRepository: MoviesRepository) {
        this.moviesRepository = moviesRepository
    }

    fun getMoviesData(): MutableLiveData<ApiResponse> {
        getMoviesJob?.cancel()
        getMoviesJob = viewModelScope.launch {
            val apiResponse = moviesRepository?.getMoviesData(pageNumber.toString())
            pageNumber++;
            mutableMoviesLiveData?.value = apiResponse

        }
        return mutableMoviesLiveData
    }
}