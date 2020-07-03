package com.example.tptallerdiseniomlapi.adapters

import Results
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tallermobiletpmlapi.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleAdapter(val onItemDetailViewClick: (articulos: Results) -> Unit) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    var articleList = ArrayList<Results>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        articleList[position].let { article ->
            holder.itemView.ArticleDescription.text = articleList[position].title
            var currency = setCurrencyTipe(articleList[position].currency_id)
            holder.itemView.articlePrice.text =
                currency.plus(articleList.get(position).price.toString())
            holder.itemView.ArticleConditionTextView.text = articleList.get(position).condition

            holder.itemView.ArticleaAviableQuantityTextView.text =
                if (articleList.get(position).available_quantity < 10) "Pocos disponibles" else "Disponibles: ".plus(
                    articleList.get(position).available_quantity.toString()
                )
            Picasso.get()
                .load(
                    articleList[position].thumbnail.replace(
                        "http",
                        "https"
                    )
                )
                .into(holder.itemView.articleImage)
            holder.itemView.setOnClickListener {
                onItemDetailViewClick(articleList[position])
            }
        }
    }

// lo movere a un companion object
    fun setCurrencyTipe(currencyId: String): String {
        var currency = ""
        when (currencyId) {
            "ARG" -> currency = "$"
            "USD" -> currency = "U\$S"
            else -> currency = "$"
        }
        return currency
    }
}


