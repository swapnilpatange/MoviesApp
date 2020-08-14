package com.app.movies.moviesapp.dagger

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        MoviesListActivityModule::class,
        ActivityBuilder::class
    )
)
interface MoviesComponent : AndroidInjector<MoviesApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MoviesApplication>()

}