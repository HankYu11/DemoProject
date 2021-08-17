package com.example.android.demoproject.data

import app.cash.turbine.test
import com.example.android.demoproject.data.domain.Agriculture
import com.example.android.demoproject.data.remote.Response
import com.example.android.demoproject.data.remote.asDomain
import com.example.android.demoproject.utils.MainCoroutineScopeRule
import com.example.android.demoproject.utils.MockDataUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class AgricultureRepositoryImplTest {
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private val mockAgricultureApi: AgricultureApi = mock()
    private val agricultureRepository = AgricultureRepositoryImpl(mockAgricultureApi, coroutineScope.dispatcher)
    private val mockAgricultureResponse = MockDataUtils.mockAgricultureResponse()
    private val mockIOException: IOException = mock()

    @Test
    fun `Given agriculture response list, When getAgricultureInMarket() called, Then expect items emit`() =
        coroutineScope.runBlockingTest {

            whenever(mockAgricultureApi.getAgricultureInMarket()).thenReturn(mockAgricultureResponse)

            agricultureRepository.getAgricultureInMarket().test {
                val item = expectItem()
                assertEquals(mockAgricultureResponse.dataList.map { it.asDomain() }, item)
                expectComplete()
            }
        }

    @Test
    fun `Given null agriculture data list, When getAgricultureInMarket() called, Then expect items emit`() =
        coroutineScope.runBlockingTest {
            whenever(mockAgricultureApi.getAgricultureInMarket()).thenReturn(Response(emptyList()))

            agricultureRepository.getAgricultureInMarket().test {
                val item = expectItem()
                assertEquals(emptyList<Agriculture>(), item)
                expectComplete()
            }
        }

    @Test
    fun `Given exception, When getCars() called, Then expect error event`() =
        runBlockingTest {
            whenever(mockAgricultureApi.getAgricultureInMarket()).thenAnswer { throw mockIOException }

            agricultureRepository.getAgricultureInMarket().test {
                val error = expectError()
                assertEquals(mockIOException, error)
            }
        }
}