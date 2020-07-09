package com.example.tallermobiletpmlapi

import Results
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebastp.data.Api
import com.example.tallermobiletpmlapi.entities.SearchResult
import com.example.tallermobiletpmlapi.utils.ConnectionChecker
import com.example.tptallerdiseniomlapi.adapters.ArticleAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var currentSearch: SearchResult? = null
    private var currentSearchTerm: String = ""
    private var adapter = ArticleAdapter({ result -> handleOnItemViewClick(result) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isConnectedInternet()
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

    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (currentSearch != null) {
            outState.putString(CURRENT_SEARCH_KEY, Gson().toJson(currentSearch))
        }
        super.onSaveInstanceState(outState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(CURRENT_SEARCH_KEY)) {
                val currentSearchJson = savedInstanceState.getString(CURRENT_SEARCH_KEY)
                currentSearch = Gson().fromJson(currentSearchJson, SearchResult::class.java)
                if (currentSearch != null) {
                    setArticleValues(currentSearch!!)
                }
            }
        }
    }

    private fun isConnectedInternet(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (!isConnected) {
            imgNoInternet.visibility = View.VISIBLE
            articleList.visibility = View.GONE
            Toast.makeText(this, getString(R.string.InternetNotAviable), Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun search(q: String) {
        if (isConnectedInternet()) {
            currentSearchTerm = q
            loading()
            //   Log.i(TAG, "Search method called with q: $q")
            Api().getListArticle(q, object : retrofit2.Callback<SearchResult> {

                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.search_call_error),
                        Toast.LENGTH_LONG
                    ).show()
                    loading()
                }

                override fun onResponse(
                    call: Call<SearchResult>,
                    response: Response<SearchResult>
                ) {
                    // Log.i(TAG,"The response of search call was:\ncode: ${response.code()}\nbody: ${response.body()?.toString()}" )
                    when (response.code()) {
                        in 200..299 -> {
                            currentSearch = response.body()!!

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
                    loading(response.isSuccessful)
                }
            })
        }
    }


    private fun loading(Results: Boolean = false) {
        if (progressBar.visibility == View.VISIBLE) {
            progressBar.visibility = View.GONE
        } else {
            progressBar.visibility = View.VISIBLE
        }
        if (Results) {
            imgNoInternet.visibility = View.GONE
            articleList.visibility = View.VISIBLE
        } else {
            articleList.visibility = View.GONE
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
