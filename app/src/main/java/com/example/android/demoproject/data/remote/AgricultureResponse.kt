package com.example.android.demoproject.data.remote

import com.example.android.demoproject.data.domain.Agriculture
import com.squareup.moshi.Json

data class Response(
    @Json(name = "Data") val dataList: List<NetAgriculture>
)

data class NetAgriculture(
    @Json(name = "Avg_Price") val avgPrice : String,
    @Json(name = "CropName") val name : String,
    @Json(name = "MarketName") val marketName: String
)

fun NetAgriculture.asDomain(): Agriculture{
    val avgPrice = "$$avgPrice"
    return Agriculture(avgPrice, name, marketName)
}