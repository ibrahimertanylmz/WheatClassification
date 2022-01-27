package com.engresearch.wheatclassification.model
import com.google.gson.annotations.SerializedName


data class PredictionResults(
        @SerializedName("prediction")
    val prediction: Prediction,
        @SerializedName("status")
    val status: String
) {
    data class Prediction(
        @SerializedName("bezostaya")
        val bezostaya: Double,
        @SerializedName("dropi-torex")
        val dropiTorex: Double,
        @SerializedName("esperia")
        val esperia: Double,
        @SerializedName("gerek")
        val gerek: Double,
        @SerializedName("kirac")
        val kirac: Double,
        @SerializedName("krasunia")
        val krasunia: Double,
        @SerializedName("maden")
        val maden: Double,
        @SerializedName("misiia")
        val misiia: Double,
        @SerializedName("mufitbey")
        val mufitbey: Double,
        @SerializedName("prediction_result")
        val predictionResult: String,
        @SerializedName("qality")
        val qality: Double,
        @SerializedName("rumeli")
        val rumeli: Double,
        @SerializedName("syrena")
        val syrena: Double,
        @SerializedName("tosunbey")
        val tosunbey: Double,
        @SerializedName("yubileynaus")
        val yubileynaus: Double
    )
}