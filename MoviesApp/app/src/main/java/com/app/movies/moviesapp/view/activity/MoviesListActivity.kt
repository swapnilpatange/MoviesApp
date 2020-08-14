package com.app.movies.moviesapp.view.activity

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.movies.moviesapp.R
import com.app.movies.moviesapp.view.adapter.GridSpacingDecoration
import com.app.movies.moviesapp.view.adapter.MoviesAdapter
import com.app.movies.moviesapp.view.customview.DrawableClickListener
import com.app.movies.moviesapp.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_movies_list.*
import org.json.JSONException
import javax.inject.Inject

class MoviesListActivity : BaseActivity<MoviesViewModel>(), View.OnClickListener,
    TextWatcher, DrawableClickListener {
    override fun onClick(target: DrawableClickListener.DrawablePosition) {
        when (target) {
            DrawableClickListener.DrawablePosition.RIGHT -> {
                closeSearchView()
                hideKeyboard()
            }
        }
    }

    override fun afterTextChanged(editable: Editable?) {
        if (moviesList.adapter != null)
            (moviesList?.adapter as MoviesAdapter).filter.filter(editable.toString())
        if (editable.toString().trim().length == 0) {
            inputSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        } else {
            inputSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_search_cancel, 0)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    @Inject
    lateinit var moviesViewModel: MoviesViewModel
    private var loadmore: Boolean = false
    override fun getViewModel(): MoviesViewModel {

        return moviesViewModel
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            (moviesList.layoutManager as GridLayoutManager).spanCount = SPAN_COUNT_LANDSCAPE
        } else {
            (moviesList.layoutManager as GridLayoutManager).spanCount = SPAN_COUNT_POTRAIT
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)
        progressBar.visibility = View.VISIBLE
        if (isNetworkConnected())
            loadListData()
        else {
            showToast(resources.getString(R.string.no_internet))
            progressBar.visibility = View.GONE
            txtNoResult.visibility = View.VISIBLE
        }
        setListener()
    }

    private fun setListener() {
        imgBack.setOnClickListener(this)
        imgSearch.setOnClickListener(this)
        inputSearch.addTextChangedListener(this)
        inputSearch.setDrawableClickListener(this)
        moviesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisiblePosition = (moviesList.layoutManager as GridLayoutManager).findLastVisibleItemPosition()

                if (!loadmore && !(moviesList.adapter as MoviesAdapter).isFiltered() && lastVisiblePosition == recyclerView.adapter!!.itemCount - 1) {
                    if (isNetworkConnected()) {
                        loadmore = true
                        moviesViewModel.getMoviesData()
                    }
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun loadListData() {
        moviesViewModel.getMoviesData().observe(this, Observer { t ->

            progressBar.visibility = View.GONE
            try {

                if (moviesList.adapter == null) {
                    pageTitle.text = t?.page?.title
                    val adapter = MoviesAdapter(this)
                    adapter.content = t?.page?.content_items?.content
                    adapter.contentFiltered = t?.page?.content_items?.content
                    moviesList.adapter = adapter
                    moviesList.layoutManager = GridLayoutManager(this, 3)
                    moviesList.addItemDecoration(GridSpacingDecoration(10))
                } else {
                    (moviesList.adapter as MoviesAdapter).content = t?.page?.content_items?.content
                    (moviesList.adapter as MoviesAdapter).contentFiltered = t?.page?.content_items?.content
                    (moviesList.adapter as MoviesAdapter).notifyDataSetChanged()
                }
                if (t!!.loadmore) {
                    loadmore = false
                } else {
                    loadmore = true
                }
                Log.d(javaClass.simpleName, "")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            Log.d(javaClass.simpleName, "")

        })


    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imgBack -> {
                onBackPressed()
            }
            R.id.imgSearch -> {
                pageTitle.visibility = View.GONE
                imgBack.visibility = View.GONE
                imgSearch.visibility = View.GONE
                inputSearch.visibility = View.VISIBLE
                inputSearch.requestFocus()
                showKeyboard()
            }
        }
    }

    override fun onBackPressed() {

        if (pageTitle.visibility == View.VISIBLE) {
            super.onBackPressed()
        } else {
            closeSearchView()
        }
    }

    fun closeSearchView() {
        pageTitle.visibility = View.VISIBLE
        imgBack.visibility = View.VISIBLE
        imgSearch.visibility = View.VISIBLE
        inputSearch.visibility = View.GONE
        inputSearch.setText("")
        if (moviesList.adapter != null)
            (moviesList?.adapter as MoviesAdapter).filter.filter("")
    }
}
