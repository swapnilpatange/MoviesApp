package com.app.movies.moviesapp.dagger

import dagger.android.DaggerApplication
import dagger.android.AndroidInjector



class MoviesApplication:DaggerApplication() {
    private val instance: MoviesApplication? = null

    override fun onCreate() {
        super.onCreate()
    }

    @Synchronized
    fun getInstance(): MoviesApplication? {
        return instance
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerMoviesComponent.builder().create(this)
    }
}