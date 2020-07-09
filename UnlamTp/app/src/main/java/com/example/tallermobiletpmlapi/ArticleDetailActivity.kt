package com.example.tallermobiletpmlapi

import android.content.Context
import com.example.tallermobiletpmlapi.entities.DescriptionArticle
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebastp.data.Api
import com.example.tallermobiletpmlapi.adapters.ImageArticleAdapter
import com.example.tallermobiletpmlapi.entities.Article
import com.example.tallermobiletpmlapi.utils.ConnectionChecker
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.activity_main.*
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

        imgBtnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, current!!.permalink)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, getString(R.string.compartirMensaje))
            startActivity(shareIntent)
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
        if (isConnectedInternet()) {
            loading()

            Api().getArticle(IdArticle, object : retrofit2.Callback<Article> {
                override fun onFailure(call: Call<Article>, t: Throwable) {
                    //      Log.e(TAG, R.string.search_call_error.toString(), t)
                    Toast.makeText(
                        this@ArticleDetailActivity,
                        getString(R.string.error_search_details_article),
                        Toast.LENGTH_LONG
                    ).show()
                    loading()
                        finish()

                }

                override fun onResponse(call: Call<Article>, response: Response<Article>) {

                    when (response.code()) {
                        in 200..299 -> {
                            setArticleDetailsValues(response.body()!!)
                            buscarDesc()

                        }
                        else -> {
                            Toast.makeText(
                                this@ArticleDetailActivity,
                                getString(ConnectionChecker.getResponseCodeDesc(response.code())),
                                Toast.LENGTH_LONG
                            ).show()
                            val intent =
                                Intent(this@ArticleDetailActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    loading(response.isSuccessful)

                    adapterImg.notifyDataSetChanged()
                }

            })
        }
    }

    private fun setArticleDetailsValues(articleDetail: Article) {
        var article = articleDetail
        textViewArticle.text = article.title
        textViewArticlePrice.text = "$ ${article?.price.toString()}"
        textViewArticleCondition.text =
            if (article.condition.equals("new")) getString(R.string.estadoNuevoArticulo) else getString(
                R.string.estadoUsadoArticulo
            )
        textViewArticleQuantitySold.text =
            getString(R.string.vendidos).plus(article.sold_quantity.toString())
        current = articleDetail
        adapterImg.picturesList = article.pictures
    }

    fun buscarDesc() {
        if (isConnectedInternet()) {
            Api().getArticleDescription(IdArticle, object : Callback<Array<DescriptionArticle>> {
                override fun onFailure(call: Call<Array<DescriptionArticle>>, t: Throwable) {
                    Toast.makeText(
                        this@ArticleDetailActivity,
                        getString(R.string.error_search_details_article),
                        Toast.LENGTH_LONG
                    ).show()
                    // me parece mas practico que ante un error como este que evita la carga del producto.
                    // Avise y lo mande directo a la actitity anterior ahorrandole un movimiento al usuario
                    finish()

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
                                getString(R.string.desc_art_not_found),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

            })
        }
    }
    private fun loading(Results: Boolean = false) {
        if (progressBarD.visibility == View.VISIBLE) {
            progressBarD.visibility = View.GONE
        } else {
            progressBarD.visibility = View.VISIBLE
        }
    }

    companion object {
        val TAG = ArticleDetailActivity::class.simpleName
        val CURRENT_SEARCH_KEY = "CURRENT_SEARCH_KEY"
        val CURRENT_SEARCH_TERM = "CURRENT_SEARCH_TERM"
    }


}

