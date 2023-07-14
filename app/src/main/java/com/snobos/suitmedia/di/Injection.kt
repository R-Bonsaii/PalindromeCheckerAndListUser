package com.snobos.suitmedia.di

import android.content.Context
import com.snobos.suitmedia.data.retrofit.ApiConfig
import com.snobos.suitmedia.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}