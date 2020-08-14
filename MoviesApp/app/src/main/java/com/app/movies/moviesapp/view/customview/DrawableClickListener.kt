package com.app.movies.moviesapp.view.customview


interface DrawableClickListener {

    enum class DrawablePosition {
        TOP, BOTTOM, LEFT, RIGHT
    }

    fun onClick(target: DrawablePosition)
}