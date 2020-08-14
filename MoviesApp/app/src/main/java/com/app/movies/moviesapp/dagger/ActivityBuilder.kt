package com.app.movies.moviesapp.dagger

import com.app.movies.moviesapp.view.activity.MoviesListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MoviesListActivityModule::class])
    internal abstract fun contributeMoviesListActivity(): MoviesListActivity
}
