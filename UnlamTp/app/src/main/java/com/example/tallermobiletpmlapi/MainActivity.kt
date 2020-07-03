package com.example.tallermobiletpmlapi

import Results
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebastp.data.Api
import com.example.tallermobiletpmlapi.entities.SearchResult
import com.example.tptallerdiseniomlapi.adapters.ArticleAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val cargaInicial =
        "Celulares" // Para que muestre un resultado por defecto (No salve los datos para mantener la busqueda anterior)
    private var currentSearch: SearchResult? = null
    private var currentSearchTerm: String = ""
    private var adapter = ArticleAdapter({ result -> handleOnItemViewClick(result) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*      val navigationView = findViewById<View>(R.id.btnNavigation) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener { item ->
          when (item.itemId) {
              R.id.busquedaNav -> {
                  intent = Intent(this, MainActivity::class.java)
                  startActivity(intent)
              }

              R.id.detalleNav -> {
                  intent = Intent(this, ArticleDetailActivity::class.java)
                  intent.putExtra("idArticle", "MLA825678604")
                  startActivity(intent)
              }

          }
          true
      }*/

        isConnectedInternet()


        /*Proximamente lo pondre en un Nav Bar con algunas otras opciones de vista (Favoritos, carrito etc)*/
        searchViewArticle.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false

            }

        })
        articleList.layoutManager = LinearLayoutManager(this)
        articleList.adapter = adapter

        if (currentSearch == null) search(cargaInicial) //CARGA POR DEFECTO cada vez q se crea la vista (para simular un busquedas recientes o recomendadas)

    }

    // La idea es hacer lo en una cmopanion object pero no lo pude implementera
// porque necesito el contexto (estoy viendo la firma de hacerlo sin pasarlo)
//SE QUE ESTA DEPRECADO,ESTUVE VIENDO LA DOCUMENTACION Y NO ENCONTRE LA PARTE QUE CORRESPONDE NECESITO MAS TIEMPO PARA ENCONTARLO
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

    private fun search(q: String) {
       if (isConnectedInternet()) {
           currentSearchTerm = q
           //   Log.i(TAG, "Search method called with q: $q")
           Api().getListArticle(q, object : retrofit2.Callback<SearchResult> {

               override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                   Toast.makeText(
                       this@MainActivity,
                       getString(R.string.search_call_error),
                       Toast.LENGTH_LONG
                   ).show()
               }

               override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                   // Log.i(TAG,"The response of search call was:\ncode: ${response.code()}\nbody: ${response.body()?.toString()}" )
                   when (response.code()) {
                       in 200..299 -> {
                           currentSearch = response.body()!!

                           // Es un asco, lo voy a mejorar con animacion. Lo mismo cunado no se encuentran imagenes
                           Toast.makeText(
                               this@MainActivity,
                               getString(R.string.Loading),
                               Toast.LENGTH_LONG
                           ).show()
                           setArticleValues(response.body()!!)

                       }
                       else -> {
                           Toast.makeText(
                               this@MainActivity,
                               getString(ConnectionChecker.getResponseCodeDesc(response.code())),
                               Toast.LENGTH_LONG
                           ).show()
                       }

                   }
               }
           })
       }
    }

    private fun setArticleValues(body: SearchResult) {
        if (body.paging.total > 0) {
            adapter.articleList = body.results
        } else {
            adapter.articleList = ArrayList()

            Toast.makeText(
                this@MainActivity,
                R.string.not_found,
                Toast.LENGTH_LONG
            )
                .show()
        }

        adapter.notifyDataSetChanged()
    }

    private fun handleOnItemViewClick(result: Results) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra("idArticle", result.id)
        startActivity(intent)

    }

    companion object {
        val TAG = MainActivity::class.simpleName
        val CURRENT_SEARCH_KEY = "CURRENT_SEARCH_KEY"
        val CURRENT_SEARCH_TERM = "CURRENT_SEARCH_TERM"
    }
}
