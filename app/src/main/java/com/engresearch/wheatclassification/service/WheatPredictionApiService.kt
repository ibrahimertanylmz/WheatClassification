package com.engresearch.wheatclassification.service

import com.engresearch.wheatclassification.model.PredictionResults
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WheatPredictionApiService {
    private val BASE_URL = "https://2kproje.solak.dev"

    val wheatPredictionApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WheatPredictionApi::class.java)

    fun getPredictionService(image: MultipartBody.Part): Single<PredictionResults> {
        return wheatPredictionApi.getPredictionResults(image)
    }
}