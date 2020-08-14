package com.app.movies.moviesapp.model

data class Page(
    var title: String,
    var total_content_items: String,
    var page_num: String,
    var page_size: String,
    var content_items: ContentItems
)
