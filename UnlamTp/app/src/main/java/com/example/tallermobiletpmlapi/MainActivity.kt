package com.example.tallermobiletpmlapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebastp.data.Api
import com.example.tallermobiletpmlapi.entities.SearchResult
import com.example.tptallerdiseniomlapi.adapters.ArticleAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var currentSearch: SearchResult? = null
    private var currentSearchTerm: String = ""
    private var adapter = ArticleAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBuscar.setOnClickListener {
            search(editTextBuscar?.text.toString())
        }

        articleList.layoutManager = LinearLayoutManager(this)
        articleList.adapter = adapter
    }

    private fun search(q: String) {
        currentSearchTerm = q
        Log.i(TAG, "Search method called with q: $q")
        Api().getListArticle(q, object : retrofit2.Callback<SearchResult> {

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                //      Snackbar.make(mainContainer, R.string.no_internet, Snackbar.LENGTH_LONG).show()
                //   toggleLoading()
                Log.e(TAG, "Search call failed", t)
            }

            override fun onResponse(
                call: Call<SearchResult>,
                response: Response<SearchResult>
            ) {
                Log.i(
                    TAG,
                    "The response of search call was:\ncode: ${response.code()}\nbody: ${response.body()
                        ?.toString()}"
                )
                when (response.code()) {
                    in 200..299 -> {
                        currentSearch = response.body()!!
                        setAlbumValues(response.body()!!)
                    }
                    404 -> Toast.makeText(
                        this@MainActivity,
                        R.string.resource_not_found,
                        Toast.LENGTH_LONG
                    )
                        .show()
                    400 -> Toast.makeText(
                        this@MainActivity,
                        R.string.bad_request,
                        Toast.LENGTH_LONG
                    )
                        .show()
                    in 500..599 -> Toast.makeText(
                        this@MainActivity,
                        R.string.server_error,
                        Toast.LENGTH_LONG
                    )
                        .show()
                    else -> Toast.makeText(
                        this@MainActivity,
                        R.string.unknown_error,
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                // toggleLoading(response.isSuccessful)
            }
        })

        //)

    }

    private fun setAlbumValues(body: SearchResult) {
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

    companion object {
        val TAG = MainActivity::class.simpleName
        val CURRENT_SEARCH_KEY = "CURRENT_SEARCH_KEY"
        val CURRENT_SEARCH_TERM = "CURRENT_SEARCH_TERM"
    }
}
