package com.snobos.suitmedia.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.snobos.suitmedia.data.PagingSource
import com.snobos.suitmedia.data.response.ItemsItem
import com.snobos.suitmedia.data.retrofit.ApiService

class UserRepository private constructor(private val apiService: ApiService){

    fun getUser(): LiveData<PagingData<ItemsItem>> {
        val pagingConfig = PagingConfig(pageSize = PAGE_SIZE)
        val pagingSourceFactory = { PagingSource(apiService) }
        return Pager(config = pagingConfig, pagingSourceFactory = pagingSourceFactory).liveData
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }

        private const val PAGE_SIZE = 20
    }
}