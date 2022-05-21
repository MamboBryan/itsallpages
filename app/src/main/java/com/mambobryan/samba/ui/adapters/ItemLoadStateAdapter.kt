package com.mambobryan.samba.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mambobryan.samba.databinding.LayoutLoadStateBinding
import timber.log.Timber
import javax.inject.Inject

/**
 * 2.10 -> Create a load state adapter to show progress
 */

class ItemLoadStateAdapter @Inject constructor() :
    LoadStateAdapter<ItemLoadStateAdapter.ItemLoadStateViewHolder>() {

    private var retry: (() -> Unit)? = null

    fun onRetryClicked(retry: () -> Unit) {
        this.retry = retry
    }

    inner class ItemLoadStateViewHolder(private val binding: LayoutLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnStateRetry.setOnClickListener { retry?.invoke() }
        }

        fun bind(loadState: LoadState) {

            /**
             * 2.11 -> Handle request Loading and Error
             */

//            when(loadState){
//                LoadState.Loading -> TODO()
//                is LoadState.NotLoading -> TODO()
//                is LoadState.Error -> TODO()
//            }

            binding.apply {
                if (loadState is LoadState.Error) tvStateError.text = loadState.error.localizedMessage

                progressState.isVisible = loadState is LoadState.Loading
                tvStateError.isVisible = loadState is LoadState.Error
                btnStateRetry.isVisible = loadState is LoadState.Error

                if (loadState.endOfPaginationReached) {
                    tvStateError.text = "Loaded All Items"
                    tvStateError.isVisible = true
                    btnStateRetry.isVisible = false
                }

            }
        }

    }

    override fun onBindViewHolder(holder: ItemLoadStateAdapter.ItemLoadStateViewHolder, loadState: LoadState) {
        Timber.i("ViewHolder bound")
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ItemLoadStateAdapter.ItemLoadStateViewHolder {
        val binding =
            LayoutLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemLoadStateViewHolder(binding)
    }

}