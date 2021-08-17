package com.example.android.demoproject

import androidx.lifecycle.*
import com.example.android.demoproject.data.AgricultureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgricultureListViewModel @Inject constructor(
    private val agricultureRepository: AgricultureRepository
): ViewModel() {

    private val _uiState = MutableLiveData<AgricultureListViewState>()
    val agricultureListViewState: LiveData<AgricultureListViewState> by lazy {
        getAgriculture()
        _uiState
    }

    val emptyOrError: LiveData<Boolean> = Transformations.map(_uiState) { state ->
        state is AgricultureListViewState.Empty || state is AgricultureListViewState.Error
    }

    fun getAgriculture() = viewModelScope.launch {
        agricultureRepository.getAgricultureInMarket()
            .onStart { _uiState.value = AgricultureListViewState.Loading }
            .catch { throwable ->  _uiState.value = AgricultureListViewState.Error(throwable) }
            .collect { result ->
                if (result.isEmpty()) {
                    _uiState.value = AgricultureListViewState.Empty
                } else {
                    _uiState.value = AgricultureListViewState.Success(result)
                }
            }
    }
}