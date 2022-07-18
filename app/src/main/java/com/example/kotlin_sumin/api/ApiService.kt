package com.example.kotlin_sumin.api

import com.example.kotlin_sumin.pojo.CoinInfoListOfData
import com.example.kotlin_sumin.pojo.CoinPriceInfoRawData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query


// Получаем значения с сервера
interface ApiService {

    // https://min-api.cryptocompare.com/data/top/totalvolfull?limit=10&tsym=USD
    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_VALUE) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY,
    ): Single<CoinInfoListOfData>

    // https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC&tsyms=USD,EUR
    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSym: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSym: String = CURRENCY,
    ): Single<CoinPriceInfoRawData>


    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_VALUE = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"

        private const val CURRENCY = "USD"
    }
}