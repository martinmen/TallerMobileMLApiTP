package com.example.tptallerdiseniomlapi.adapters

import Results
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tallermobiletpmlapi.ArticleDetailActivity
import com.example.tallermobiletpmlapi.MainActivity
import com.example.tallermobiletpmlapi.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article.view.*

// le pase como parametro el MainActivity porque el contructor de Inten pide un contexto Pero por alguna razon el starActivity() pide un contexto en vez de un Intent Entiendo que deberia pedir un Intent.
class ArticleAdapter(mainActivity: MainActivity) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    var articleList = ArrayList<Results>()
var context = mainActivity
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.item_article,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
    return articleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            articleList[position].let {
                article ->
                holder.itemView.ArticleDescription.text = articleList[position].title
                var currency = setCurrencyTipe(articleList[position].currency_id)
                holder.itemView.articlePrice.text = currency.plus(articleList.get(position).price.toString())
                holder.itemView.ArticleConditionTextView.text = articleList.get(position).condition

                holder.itemView.ArticleaAviableQuantityTextView.text = if (articleList.get(position).available_quantity<10) "Pocos disponibles" else "Disponibles: ".plus(articleList.get(position).available_quantity.toString()) // agregue esa condicion para jugar un poco
                Picasso.get()
                    .load(articleList[position].thumbnail.replace( "http", "https")) //No trae con http, tengo que fijarme como arreglarlo
                    .into(holder.itemView.articleImage)
               //Tratar de aplicar boton ir a detalle para cada item /o mismo un clickListener sobre el holder
                // holder.itemView.setOnClickListener{
              //  val intent = Intent(ArticleDetailActivity::class.java)
                //  startActivity(intent as Context)


        }
            }


    }
fun getContext (cont:Context):Context{
    return cont

}

    fun setCurrencyTipe (currencyId: String): String{
        var currency = ""
        when(currencyId){
            "ARG" ->  currency = "$"
            "USD" -> currency = "U\$S"
            else -> currency = "$"
        }
        return currency
    }
