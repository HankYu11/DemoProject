package com.example.android.demoproject.data

import com.example.android.demoproject.data.domain.Agriculture
import com.example.android.demoproject.data.remote.asDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface AgricultureRepository {
    fun getAgricultureInMarket(): Flow<List<Agriculture>>
}

class AgricultureRepositoryImpl(
    private val agricultureApi: AgricultureApi,
    private val dispatcher: CoroutineDispatcher
) : AgricultureRepository {

    override fun getAgricultureInMarket(): Flow<List<Agriculture>> = flow {
        agricultureApi.getAgricultureInMarket().dataList
            .map { it.asDomain() }
            .also { emit(it) }
    }.flowOn(dispatcher)
}