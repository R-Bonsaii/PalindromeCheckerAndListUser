package com.snobos.suitmedia.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snobos.suitmedia.data.response.ItemsItem
import com.snobos.suitmedia.data.retrofit.ApiService

class PagingSource(private val apiService: ApiService) : PagingSource<Int, ItemsItem>() {

    override fun getRefreshKey(state: PagingState<Int, ItemsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemsItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            Log.d("UserPagingSource", "Loading page: $page")

            val response = apiService.getUsers(page, params.loadSize)
            val responseData = response.body()?.data

            if (responseData != null) {
                val nextKey = if (responseData.size < params.loadSize) null else page + 1
                LoadResult.Page(
                    data = responseData,
                    prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(NullPointerException("User data is null"))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}