package com.snobos.suitmedia.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snobos.suitmedia.data.response.ItemsItem
import com.snobos.suitmedia.repository.UserRepository

class ThirdViewModel(private val repository: UserRepository) : ViewModel() {

    val user: LiveData<PagingData<ItemsItem>> by lazy {
        repository.getUser().cachedIn(viewModelScope)
    }
}