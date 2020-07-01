package com.example.tallermobiletpmlapi

import com.example.tallermobiletpmlapi.entities.DescriptionArticle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebastp.data.Api
import com.example.tallermobiletpmlapi.adapters.ImageArticleAdapter
import com.example.tallermobiletpmlapi.entities.Article
import kotlinx.android.synthetic.main.activity_article_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleDetailActivity : AppCompatActivity() {
    var IdArticle = ""
    var current: Article? = null
    private var adapterImg = ImageArticleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        val bundle = intent.extras
        if (bundle != null) {
            IdArticle = bundle.getString("idArticle").toString()
        }

        imagesArticleDetail.layoutManager = LinearLayoutManager(this)
        imagesArticleDetail.adapter = adapterImg
        /*       val navigationView = findViewById<View>(R.id.btnNavigation) as BottomNavigationView
               navigationView.setOnNavigationItemSelectedListener { item ->
                   when (item.itemId) {
                       R.id.busquedaNav -> {
                           intent = Intent(this, MainActivity::class.java)
                           startActivity(intent)
                       }
                       R.id.detalleNav -> {
                           intent = Intent(this, ArticleDetailActivity::class.java)
                           intent.putExtra("idArticle", IdArticle)
                           startActivity(intent)
                       }
                   }
                   true
               }*/
        imgBtnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, current!!.permalink)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, getString(R.string.compartirMensaje))
            startActivity(shareIntent)
        }

        btnIrBuscarArticulos.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        buscarArticleDetails()
        buscarDesc()
    }

    private fun buscarArticleDetails() {
        Api().getArticle(IdArticle, object : Callback<Article> {
            override fun onFailure(call: Call<Article>, t: Throwable) {
                Log.e(TAG, "Search call failed", t)
            }

            override fun onResponse(call: Call<Article>, response: Response<Article>) {
                if (response.isSuccessful) {
                    var article = response.body()
                    textViewArticle.text = "${article?.title}"
                    textViewArticlePrice.text = "$ ${article?.price.toString()}"
                    textViewArticleCondition.text =
                        if (article?.condition.equals("new")) getString(R.string.estadoNuevoArticulo) else getString(
                            R.string.estadoUsadoArticulo
                        )
                    textViewArticleQuantitySold.text =
                        getString(R.string.vendidos).plus(article?.sold_quantity.toString())
                    current = response.body()
                    adapterImg.picturesList = article!!.pictures
                } else {
                    adapterImg.picturesList = ArrayList()
                    Toast.makeText(
                        this@ArticleDetailActivity,
                        getString(R.string.errorArticuloNoEncontrado),
                        Toast.LENGTH_LONG
                    ).show()
                }
                adapterImg.notifyDataSetChanged()
            }
        })


    }

    fun buscarDesc() {
        Api().getArticleDescription(IdArticle, object : Callback<Array<DescriptionArticle>> {
            override fun onFailure(call: Call<Array<DescriptionArticle>>, t: Throwable) {
                Log.e(TAG, "Search call failed", t)
            }

            override fun onResponse(
                call: Call<Array<DescriptionArticle>>,
                response: Response<Array<DescriptionArticle>>
            ) {

                if (response.isSuccessful) {
                    var articleDesc = response.body()
                    textViewArticleDescriptionBody.text = "${articleDesc?.get(0)?.plain_text}"

                }

            }

        })
    }

    companion object {
        val TAG = ArticleDetailActivity::class.simpleName
        val CURRENT_SEARCH_KEY = "CURRENT_SEARCH_KEY"
        val CURRENT_SEARCH_TERM = "CURRENT_SEARCH_TERM"
    }


}

