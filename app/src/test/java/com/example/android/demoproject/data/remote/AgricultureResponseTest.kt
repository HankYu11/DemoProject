package com.example.android.demoproject.data.remote

import com.example.android.demoproject.utils.MockDataUtils
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertEquals
import org.junit.Test

class AgricultureResponseTest {

    @Test
    fun `Given Agriculture, When asDomain() called, Then avgPrice mapping correctly`() {
        val agricultureResponse = MockDataUtils.mockAgricultureResponse()
        val agricultureList = agricultureResponse.dataList
        val result = agricultureList.map {
            it.asDomain().avgPrice
        }
        val expect = listOf("$50", "$80")
        assertThat(result, `is`(expect))
    }
}