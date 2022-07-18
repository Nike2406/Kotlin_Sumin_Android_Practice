package com.example.kotlin_sumin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.kotlin_sumin.api.ApiFactory
import com.example.kotlin_sumin.database.AppDatabase
import com.example.kotlin_sumin.pojo.CoinPriceInfo
import com.example.kotlin_sumin.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo(limit = 30)
            .map { it ->
                it.data
                    ?.map { it.coinInfo?.name }
                    ?.joinToString(",")
                    .toString()
            }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSym = it) }
            .map { getPriceListFormRawData(it) }
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("TEST_OF_LOADING_DATA", "Success: $it")
            },
                {
                    Log.d("TEST_OF_LOADING_DATA", "Failure: ${it.message}")
                })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFormRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()

        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java,
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}