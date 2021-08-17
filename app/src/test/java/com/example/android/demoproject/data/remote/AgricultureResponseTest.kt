package com.example.android.demoproject.data.remote

import com.example.android.demoproject.utils.MockDataUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class AgricultureResponseTest {

    @Test
    fun `Given Agriculture, When asDomain() called, Then avgPrice mapping correctly`(){
        val agricultureResponse = MockDataUtils.mockAgricultureResponse()
        val agricultureList = agricultureResponse.dataList
        val result = agricultureList[0].asDomain().avgPrice

        assertEquals("$50", result)
    }
}