package com.app.movies.moviesapp.view.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Gravity
import androidx.annotation.Nullable
import dagger.android.support.DaggerAppCompatActivity
import androidx.lifecycle.ViewModel
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


abstract class BaseActivity<T : ViewModel> : DaggerAppCompatActivity() {

    private var viewModel: T? = null
    val SPAN_COUNT_POTRAIT = 3
    val SPAN_COUNT_LANDSCAPE = 7

    /**
     *
     * @return view model instance
     */
    abstract fun getViewModel(): T

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = if (viewModel == null) getViewModel() else viewModel
    }

    fun showKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
    fun hideKeyboard(){
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun isNetworkConnected(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }

    fun showToast(text:String){
        val toast=Toast.makeText(this,text,Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }
}
