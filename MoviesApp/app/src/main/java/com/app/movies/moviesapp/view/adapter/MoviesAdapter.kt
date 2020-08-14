package com.app.movies.moviesapp.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.app.movies.moviesapp.R
import com.app.movies.moviesapp.model.Content
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movies_item.view.*

class MoviesAdapter(var activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {


    var content: ArrayList<Content>? = null
    var contentFiltered: ArrayList<Content>? = null
    fun isFiltered():Boolean{

       return (contentFiltered?.size != content?.size)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.movies_item, null)
        return MoviesViewHolder(view, activity)
    }

    override fun getItemCount(): Int {
        return contentFiltered?.let { it.size } ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MoviesViewHolder) {
            holder.bindItem(contentFiltered!!.get(position))
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                contentFiltered = filterResults?.values as ArrayList<Content>

                // refresh the list with filtered data
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.length < 3) {
                    contentFiltered = content
                } else {
                    var contentfilter = ArrayList<Content>()
                    for (row in contentFiltered!!) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name.toLowerCase().contains(charString.toLowerCase())) {
                            contentfilter.add(row)
                        }
                    }
                    contentFiltered = contentfilter
                }

                val filterResults = FilterResults()
                filterResults.values = contentFiltered
                return filterResults
            }

        }
    }
}


class MoviesViewHolder(itemView: View, var activity: Activity) : RecyclerView.ViewHolder(itemView) {


    fun bindItem(content: Content) {
        itemView?.name.text = content.name
        Glide.with(activity)
            .load(activity.resources.getString(R.string.image_physical_path) + content.poster_image)
            .apply(RequestOptions().placeholder(R.drawable.placeholder_for_missing_posters).error(R.drawable.placeholder_for_missing_posters))
            .into(itemView?.poster_image)
    }
}

