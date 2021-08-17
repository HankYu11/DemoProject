package com.example.android.demoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.android.demoproject.databinding.ActivityAgricultureListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgricultureListActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAgricultureListBinding

    private val viewModel: AgricultureListViewModel by viewModels()
    private val adapter by lazy { AgricultureAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityAgricultureListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setupViews()
        setupObservers()
    }

    private fun setupViews(){
        with(viewBinding) {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getAgriculture()
            }
        }
    }

    private fun setupObservers(){
        viewModel.agricultureListViewState.observe(this){ state ->
            showViewState(state)
        }

        viewModel.emptyOrError.observe(this){ hasErrorOrEmpty ->
            viewBinding.errorText.visibility = if (hasErrorOrEmpty) View.VISIBLE else View.GONE
            viewBinding.recyclerView.visibility = if (hasErrorOrEmpty) View.GONE else View.VISIBLE
        }
    }

    private fun showViewState(state: AgricultureListViewState) {
        when (state) {
            is AgricultureListViewState.Success -> {
                adapter.submitList(state.data)
            }
            is AgricultureListViewState.Error -> {
                adapter.submitList(emptyList()) // demo purpose, prevent list flashes when loading
                viewBinding.errorText.text = getString(R.string.something_went_wrong)
            }
            is AgricultureListViewState.Empty -> {
                adapter.submitList(emptyList()) // demo purpose, prevent list flashes when loading
                viewBinding.errorText.text = getString(R.string.no_data_available)
            }
            AgricultureListViewState.Loading -> {
                viewBinding.swipeRefreshLayout.isRefreshing = true
            }
        }

        if (state != AgricultureListViewState.Loading) {
            viewBinding.swipeRefreshLayout.isRefreshing = false
        }
    }
}