package com.engresearch.wheatclassification.service

import com.engresearch.wheatclassification.model.PredictionResults
import io.reactivex.Single
import okhttp3.MultipartBody

import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface WheatPredictionApi {
    @Multipart
    @POST("/predict")
    fun getPredictionResults(@Part filePart: MultipartBody.Part): Single<PredictionResults>
}