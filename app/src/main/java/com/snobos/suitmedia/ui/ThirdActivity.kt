package com.snobos.suitmedia.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.snobos.suitmedia.ViewModelFactory
import com.snobos.suitmedia.adapter.LoadingStateAdapter
import com.snobos.suitmedia.adapter.UserAdapter
import com.snobos.suitmedia.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var thirdViewModel: ThirdViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        thirdViewModel = obtainViewModel()
        adapter = UserAdapter()

        setupRecyclerView()
        observeUser()

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun obtainViewModel(): ThirdViewModel {
        val factory = ViewModelFactory.getInstance(this)
        return ViewModelProvider(this, factory)[ThirdViewModel::class.java]
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.itemCard.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.itemCard.addItemDecoration(itemDecoration)
        binding.itemCard.adapter = adapter.withLoadStateFooter(LoadingStateAdapter())
    }

    private fun observeUser() {
        thirdViewModel.user.observe(this) { pagingData ->
            adapter.addLoadStateListener { loadState ->
                binding.tvDataEmpty.isGone = !(adapter.itemCount <= 0 && !loadState.source.refresh.endOfPaginationReached)
            }
            adapter.submitData(lifecycle, pagingData)
        }
    }
}