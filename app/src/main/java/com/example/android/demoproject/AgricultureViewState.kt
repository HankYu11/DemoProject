package com.example.android.demoproject

import com.example.android.demoproject.data.domain.Agriculture

sealed class AgricultureListViewState {
    object Loading : AgricultureListViewState()
    data class Success(val data: List<Agriculture>) : AgricultureListViewState()
    data class Error(val throwable: Throwable) : AgricultureListViewState()
    object Empty : AgricultureListViewState()
}