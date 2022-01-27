package com.engresearch.wheatclassification.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engresearch.wheatclassification.model.PredictionResults
import com.engresearch.wheatclassification.service.WheatPredictionApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MainViewModel:ViewModel() {
    private val wheatPredictionApi = WheatPredictionApiService()
    private val disposable = CompositeDisposable()
    val prediction_data = MutableLiveData<PredictionResults>()

    fun getData(file: File){
        getDataFromAPI(file)
    }

    private fun getDataFromAPI(file: File) {

        var filePart : MultipartBody.Part = MultipartBody.Part.createFormData(
            "image", "wheatImage", RequestBody.create(
                MediaType.parse(
                    "image/*"
                ), file
            )
        )

        disposable.add(
            wheatPredictionApi.getPredictionService(filePart)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PredictionResults>() {
                    override fun onSuccess(t: PredictionResults) {
                        Log.d("MainViewModel", "onSuccess -> SUCCCCCCCESSS")
                        prediction_data.value = t
                        println(prediction_data.value!!.prediction)
                    }
                    override fun onError(e: Throwable) {
                        Log.d("MainViewModel", "onError -> $e")
                    }
                })
        )
    }
}