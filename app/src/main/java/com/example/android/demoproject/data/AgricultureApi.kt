package com.example.android.demoproject.data

import com.example.android.demoproject.data.remote.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AgricultureApi {

    @GET("AgriProductsTransType/")
    suspend fun getAgricultureInMarket(): Response

    companion object {
        const val BASE_URL = "https://data.coa.gov.tw/api/v1/"
    }
}