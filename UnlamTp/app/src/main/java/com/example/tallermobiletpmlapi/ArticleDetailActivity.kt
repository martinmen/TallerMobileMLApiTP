package com.example.tallermobiletpmlapi

import android.content.Context
import com.example.tallermobiletpmlapi.entities.DescriptionArticle
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        if (!isConnectedInternet()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else
            buscarArticleDetails()
    }

    private fun isConnectedInternet(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (!isConnected) {
            Toast.makeText(this, getString(R.string.InternetNotAviable), Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun buscarArticleDetails() {
        if (isConnectedInternet()){
        Api().getArticle(IdArticle, object : retrofit2.Callback<Article> {
            override fun onFailure(call: Call<Article>, t: Throwable) {
                //      Log.e(TAG, R.string.search_call_error.toString(), t)
            }

            override fun onResponse(call: Call<Article>, response: Response<Article>) {

                when (response.code()) {
                    in 200..299 -> {
                        setArticleDetailsValues(response.body()!!)
                        // Es un asco, lo voy a mejorar con animacion. Lo mismo cunado no se encuentran imagenes
                        Toast.makeText(
                            this@ArticleDetailActivity,
                            getString(R.string.Loading),
                            Toast.LENGTH_LONG
                        ).show()
                        buscarDesc()
                    }
                    else -> {
                        Toast.makeText(
                            this@ArticleDetailActivity,
                            getString(ConnectionChecker.getResponseCodeDesc(response.code())),
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(this@ArticleDetailActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                adapterImg.notifyDataSetChanged()
            }

        })
        }
    }

    private fun setArticleDetailsValues(articleDetail: Article) {
        var article = articleDetail
        textViewArticle.text = "${article?.title}"
        textViewArticlePrice.text = "$ ${article?.price.toString()}"
        textViewArticleCondition.text =
            if (article?.condition.equals("new")) getString(R.string.estadoNuevoArticulo) else getString(
                R.string.estadoUsadoArticulo
            )
        textViewArticleQuantitySold.text =
            getString(R.string.vendidos).plus(article?.sold_quantity.toString())
        current = articleDetail
        adapterImg.picturesList = article!!.pictures
    }

    fun buscarDesc() {
        if (isConnectedInternet()){
            Api().getArticleDescription(IdArticle, object : Callback<Array<DescriptionArticle>> {
                override fun onFailure(call: Call<Array<DescriptionArticle>>, t: Throwable) {
                    Toast.makeText(
                        this@ArticleDetailActivity,
                        getString(R.string.search_call_error),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(
                    call: Call<Array<DescriptionArticle>>,
                    response: Response<Array<DescriptionArticle>>
                ) {

                    when (response.code()) {
                        in 200..299 -> {
                            var articleDesc = response.body()
                            textViewArticleDescriptionBody.text =
                                "${articleDesc?.get(0)?.plain_text}"
                        }
                        else -> {
                            Toast.makeText(
                                this@ArticleDetailActivity,
                                "Descripcion del Articulo no encontrada",
                                Toast.LENGTH_LONG
                            ).show()
                            // Podrias aplicar esto pero muestra por un instante los datos de la otra llamada y queda mal.
                            // Tengo que implementar algo mejor en el diseño, para le reentraga lo hago.
                            /*Toast.makeText(this@ArticleDetailActivity, getString(ConnectionChecker.getResponseCodeDesc(response.code())),Toast.LENGTH_LONG).show()
                        val intent = Intent(this@ArticleDetailActivity, MainActivity::class.java)
                        startActivity(intent)*/
                        }
                    }
                }

            })
        }
    }

    companion object {
        val TAG = ArticleDetailActivity::class.simpleName
        val CURRENT_SEARCH_KEY = "CURRENT_SEARCH_KEY"
        val CURRENT_SEARCH_TERM = "CURRENT_SEARCH_TERM"
    }


}

