package com.app.movies.moviesapp.dagger

import com.app.movies.moviesapp.api.MoviesRepository
import com.app.movies.moviesapp.viewmodel.MoviesViewModel
import dagger.Module
import dagger.Provides


@Module
class MoviesListActivityModule() {

    @Provides
    fun provideMoviesRepository(): MoviesRepository {
        return MoviesRepository()
    }

    @Provides
    fun provideMoviesListActivityViewModel(moviesRepository: MoviesRepository): MoviesViewModel {
        return MoviesViewModel(moviesRepository)
    }
}