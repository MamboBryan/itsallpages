package com.mambobryan.samba.ui.characters

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.mambobryan.samba.R
import com.mambobryan.samba.databinding.FragmentTemplateBinding
import com.mambobryan.samba.ui.adapters.CharacterAdapter
import com.mambobryan.samba.ui.adapters.ItemLoadStateAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RemoteWithCacheFragment : Fragment(R.layout.fragment_template) {

    private val binding by viewBinding(FragmentTemplateBinding::bind)
    private val viewModel: CharactersViewModel by activityViewModels()

    @Inject
    lateinit var adapter: CharacterAdapter

    @Inject
    lateinit var header: ItemLoadStateAdapter

    @Inject
    lateinit var footer: ItemLoadStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                // Show a retry header if there was an error refreshing, and items were previously
                // cached OR default to the default prepend state
                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && adapter.itemCount > 0 }
                    ?: loadState.prepend

                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                // show empty list
                binding.layoutState.stateEmpty.isVisible = isListEmpty
                // Only show the list if refresh succeeds, either from the the local db or the remote.
                binding.layoutState.stateContent.isVisible =
                    loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                binding.layoutState.stateLoading.isVisible =
                    loadState.mediator?.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                binding.layoutState.buttonRetry.isVisible =
                    loadState.mediator?.refresh is LoadState.Error && adapter.itemCount == 0
                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    val error = "\uD83D\uDE28 Wooops ${it.error}"
                    binding.layoutState.tvError.text = error
                    Toast.makeText(
                        requireContext(),
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }

        lifecycleScope.launch {
            viewModel.articlesWithCache.collectLatest {
                adapter.submitData(it)
            }
        }

    }

    private fun initViews() {
        binding.toolbar.title = "Remote & Cache"

        binding.layoutState.tvError.text = "Couldn't Load Characters"
        binding.layoutState.tvEmpty.text = "No Character Found"

        binding.layoutState.buttonRetry.setOnClickListener { adapter.refresh() }

        header.onRetryClicked { adapter.retry() }
        footer.onRetryClicked { adapter.retry() }

        adapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = footer
        )

        binding.layoutState.recyclerView.adapter = adapter

        initSomethingAwesome()

    }

    private fun initSomethingAwesome() {
        binding.apply {
            toolbar.inflateMenu(R.menu.menu_extras)
            toolbar.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.composeFragment -> {
                        findNavController().navigate(R.id.action_remoteWithCacheFragment_to_composeFragment)
                        true
                    }
                    else -> {
                        false
                    }

                }
            }
        }
    }

}