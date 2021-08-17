package com.example.android.demoproject.utils

import com.example.android.demoproject.data.remote.NetAgriculture
import com.example.android.demoproject.data.remote.Response

object MockDataUtils {

    fun mockAgricultureResponse(): Response {
        return Response(
            listOf(
                mockAgriculture("木瓜", "50", "台北市場"),
                mockAgriculture("茄子", "80", "桃園市場")
            )
        )
    }

    fun mockAgriculture(name: String, price: String, marketName: String) =
        NetAgriculture(price, name, marketName)

}