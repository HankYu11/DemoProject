package com.example.android.demoproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.demoproject.data.AgricultureRepository
import com.example.android.demoproject.data.domain.Agriculture
import com.example.android.demoproject.data.remote.asDomain
import com.example.android.demoproject.utils.FakeAgricultureRepository
import com.example.android.demoproject.utils.MainCoroutineScopeRule
import com.example.android.demoproject.utils.MockDataUtils
import com.example.android.demoproject.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class AgricultureListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private val mockRepository: AgricultureRepository = mock()
    private val viewStateObserver: Observer<AgricultureListViewState> = mock()
    private val emptyOrErrorObserver: Observer<Boolean> = mock()
    private lateinit var viewModel: AgricultureListViewModel
    private val fakeRepository = FakeAgricultureRepository()

    @Before
    fun setUp() {
        viewModel = AgricultureListViewModel(fakeRepository)
        coroutineScope.pauseDispatcher()
    }

    @Test
    fun getAgriculture_updateTheViewState() = coroutineScope.runBlockingTest {
        // When called getAgriculture
        viewModel.getAgriculture()
        //Then the view state should be loading to success
        assertThat(viewModel.agricultureListViewState.getOrAwaitValue(), `is`(AgricultureListViewState.Loading))
        coroutineScope.resumeDispatcher()
        assertThat(viewModel.agricultureListViewState.getOrAwaitValue(), `is`(AgricultureListViewState.Success(data = listOf(Agriculture("80","木瓜", "台北")))))

    }

    //region ViewState
    @Test
    fun `When getCars() called, Then viewState first value is Loading`() =
        runBlockingTest {
            AgricultureListViewModel(mockRepository).apply {
                agricultureListViewState.observeForever(viewStateObserver)
            }
            coroutineScope.resumeDispatcher()

            argumentCaptor<AgricultureListViewState> {
                verify(viewStateObserver, times(2)).onChanged(capture())
                assertEquals(AgricultureListViewState.Loading, firstValue)
            }
        }

//    @Test
//    fun `Given cars, When getCars() called, Then viewState last value is Success with data`() =
//        runBlockingTest {
//            val mockCars = MockDataUtils.mockAgriculture()
//            whenever(mockRepository.getCars(any())).thenReturn(flowOf(mockCars))
//
//            CarListViewModel(mockRepository).apply {
//                carListViewState.observeForever(viewStateObserver)
//            }
//            coroutineScope.resumeDispatcher()
//
//            argumentCaptor<AgricultureListViewState> {
//                verify(viewStateObserver, times(2)).onChanged(capture())
//                assertEquals(AgricultureListViewState.Success(mockCars), lastValue)
//            }
//        }
//
//    @Test
//    fun `Given empty cars, When getCars() called, Then viewState last value is Empty`() =
//        runBlockingTest {
//            whenever(mockRepository.getCars(any())).thenReturn(flowOf(emptyList()))
//
//            CarListViewModel(mockRepository).apply {
//                carListViewState.observeForever(viewStateObserver)
//            }
//            coroutineScope.resumeDispatcher()
//
//            argumentCaptor<AgricultureListViewState> {
//                verify(viewStateObserver, times(2)).onChanged(capture())
//                assertEquals(AgricultureListViewState.Empty, lastValue)
//            }
//        }
//    //endregion
//
//    //region emptyOrError
//    @Test
//    fun `When getCars() called, Then emptyOrError first value is false`() =
//        runBlockingTest {
//            CarListViewModel(mockRepository).apply {
//                carListViewState.observeForever(viewStateObserver)
//                emptyOrError.observeForever(emptyOrErrorObserver)
//            }
//            coroutineScope.resumeDispatcher()
//
//            argumentCaptor<Boolean> {
//                verify(emptyOrErrorObserver, times(2)).onChanged(capture())
//                Assert.assertFalse(firstValue)
//            }
//        }
//
//    @Test
//    fun `Given cars, When getCars() called, Then emptyOrError last value is false`() =
//        runBlockingTest {
//            val mockCars = MockDataUtils.mockCars()
//            whenever(mockRepository.getCars(any())).thenReturn(flowOf(mockCars))
//
//            CarListViewModel(mockRepository).apply {
//                carListViewState.observeForever(viewStateObserver)
//                emptyOrError.observeForever(emptyOrErrorObserver)
//            }
//            coroutineScope.resumeDispatcher()
//
//            argumentCaptor<Boolean> {
//                verify(emptyOrErrorObserver, times(2)).onChanged(capture())
//                Assert.assertFalse(lastValue)
//            }
//        }
//
//    @Test
//    fun `Given empty cars, When getCars() called, Then emptyOrError last value is true`() =
//        runBlockingTest {
//            whenever(mockRepository.getCars(any())).thenReturn(flowOf(emptyList()))
//
//            CarListViewModel(mockRepository).apply {
//                carListViewState.observeForever(viewStateObserver)
//                emptyOrError.observeForever(emptyOrErrorObserver)
//            }
//            coroutineScope.resumeDispatcher()
//
//            argumentCaptor<Boolean> {
//                verify(emptyOrErrorObserver, times(2)).onChanged(capture())
//                Assert.assertTrue(lastValue)
//            }
//        }
//    //endregion
//
//    //region GetCars
//    @Test
//    fun `When getCars() called, Then CarRepository getCars(NORMAL) is called`() {
//        val viewModel = CarListViewModel(mockRepository)
//        coroutineScope.resumeDispatcher()
//
//        viewModel.getCars()
//
//        verify(mockRepository).getCars(FetchEndpoint.NORMAL)
//    }
//
//    @Test
//    fun `When getCarsEmpty() called, Then CarRepository getCars(EMPTY) is called`() {
//        val viewModel = CarListViewModel(mockRepository)
//        coroutineScope.resumeDispatcher()
//
//        viewModel.getCarsEmpty()
//
//        verify(mockRepository).getCars(FetchEndpoint.EMPTY)
//    }
//
//    @Test
//    fun `When getCarsError() called, Then CarRepository getCars(ERROR) is called`() {
//        val viewModel = CarListViewModel(mockRepository)
//        coroutineScope.resumeDispatcher()
//
//        viewModel.getCarsError()
//
//        verify(mockRepository).getCars(FetchEndpoint.ERROR)
//    }

    //endregion
}