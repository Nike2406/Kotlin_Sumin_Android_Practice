package com.example.kotlin_sumin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol.toString()).observe(this, Observer {
            findViewById<TextView>(R.id.tvPrice).text = it.price.toString()
            findViewById<TextView>(R.id.tvMinPrice).text = it.lowDay.toString()
            findViewById<TextView>(R.id.tvMaxPrice).text = it.highDay.toString()
            findViewById<TextView>(R.id.tvLastMarket).text = it.lastMarket.toString()
            findViewById<TextView>(R.id.tvUpdated).text = it.getFormattedTime()

            findViewById<TextView>(R.id.tvFromSymbol).text = it.fromSymbol
            findViewById<TextView>(R.id.tvToSymbol).text = it.toSymbol

            Picasso.get().load(it.getFullImageUrl()).into(findViewById<ImageView>(R.id.ivLogoCoin))
        })
    }


    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}