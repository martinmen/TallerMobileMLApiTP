package com.example.tallermobiletpmlapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tallermobiletpmlapi.R
import com.example.tptallerdiseniomlapi.entities.Pictures
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_detail.view.*

class ImageArticleAdapter : RecyclerView.Adapter<ImageArticleAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    var picturesList = ArrayList<Pictures>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.image_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return picturesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        picturesList[position].let { pictures ->
            Picasso.get()
                .load(pictures.secure_url)
                .into(holder.itemView.articleListImage)
        }
    }
}