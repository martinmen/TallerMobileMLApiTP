package com.example.tallermobiletpmlapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.pruebastp.data.Api
import com.example.pruebastp.data.Article
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleDetailActivity : AppCompatActivity() {
    var IdArticle = ""

    var Imagenes = arrayOf(
        "https://mla-s1-p.mlstatic.com/686897-MLA32557197254_102019-O.jpg",
        "https://mla-s1-p.mlstatic.com/686897-MLA32557197254_102019-O.jpg",
        "https://mla-s1-p.mlstatic.com/726691-MLA32557437038_102019-O.jpg")
    var current : Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        val bundle = intent.extras
        if (bundle != null) {
            IdArticle = bundle.getString("idArticle").toString()
        }
        Picasso.get()
            .load("https://http2.mlstatic.com/frontend-assets/ui-navigation/5.6.0/mercadolibre/logo__large_plus.png")
            .into(imageViewML)





        val navigationView = findViewById<View>(R.id.btnNavigation) as BottomNavigationView

        navigationView.setOnNavigationItemSelectedListener {
                item ->
            when(item.itemId){
                R.id.busquedaNav ->{intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)}

                R.id.detalleNav ->  {intent = Intent(this,ArticleDetailActivity::class.java)
                    intent.putExtra("idArticle", "MLA825678604")
                    startActivity(intent)}

            }
            true
        }




        val carouselView = findViewById(R.id.carouselViewImgProduct) as CarouselView;
        carouselView.setPageCount(Imagenes.size); // Esto tiene que venir si o si con algo sino no me carga el carrousel. (Por ahora lo dejo asi porque esto seguramente lo llamare de la vista de busquedas
        carouselView.setImageListener(imageListener);

        btnIrBuscarArticulos.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
   //     btnBuscar.setOnClickListener { buscarConCarrusel() } //Esto se debera ejecutar en el Intent de la Vista de busqueda de articulos
        buscarConCarrusel() // Se deja con el fin de agilizar la carga de datos para ir viendo mas rapido la vista y modelar

    }
   //

    private fun buscarConCarrusel() {
      //  Api().getArticle(editTextProductoBuscar?.text.toString(), object : Callback<Article> { //Esta funcion provablemente se mueva OOO seguramente reciba el ID desde el Intent de la vista anterior(MUY posible)
            Api().getArticle(IdArticle.toString(), object : Callback<Article> {

                override fun onFailure(call: Call<Article>, t: Throwable) {
                Toast.makeText(this@ArticleDetailActivity, "Ocurriò un error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Article>, response: Response<Article>) {
                if (response.isSuccessful) {
                    var article = response.body()
                    textViewArticle.text = "${article?.title}"
                    textViewArticlePrice.text = "$ ${article?.price}"

                    textViewArticleCondition.text = if(article?.condition.equals("new")) "Nuevo-" else "Usado-"
                    textViewArticleQuantitySold.text = "Vendidos: ${article?.sold_quantity.toString()}"
                    current = response.body()
                    lateinit var imagesArray: Array<String>
                  //  var adapatador: AdaptadorViewPager

                } else {
                    Toast.makeText(this@ArticleDetailActivity, "Item no encontrado", Toast.LENGTH_LONG)
                        .show()
                }


            }
        })
    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here
                //var verImagen = arrayOf(current?.pictures?.get(position)?.secure_url)
                Picasso.get().load(Imagenes.get(position)) // solo me muestra la ultima, nose si las demas no cargan porq. Me trae al buscar por segunda vez.
                    .fit()

                    .into(imageView)
            }
        }
    }

