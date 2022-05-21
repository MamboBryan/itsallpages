package com.mambobryan.samba.ui.characters

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.mambobryan.samba.R
import com.mambobryan.samba.databinding.FragmentTemplateBinding
import com.mambobryan.samba.ui.adapters.CharacterAdapter
import com.mambobryan.samba.ui.adapters.ItemLoadStateAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RemoteOnlyFragment : Fragment(R.layout.fragment_template) {

    private val binding by viewBinding(FragmentTemplateBinding::bind)
    private val viewModel: CharactersViewModel by activityViewModels()

    @Inject
    lateinit var characterAdapter: CharacterAdapter

    @Inject
    lateinit var footer: ItemLoadStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        /**
         * 2.8 -> Handle the load states
         */
        lifecycleScope.launch {
            characterAdapter.loadStateFlow.collectLatest { loadState ->

                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && characterAdapter.itemCount == 0
                // show empty list
                binding.layoutState.stateEmpty.isVisible = isListEmpty
                // Only show the list if refresh succeeds.
                binding.layoutState.stateContent.isVisible = !isListEmpty
                // Show loading spinner during initial load or refresh.
                binding.layoutState.stateLoading.isVisible =
                    loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                binding.layoutState.stateError.isVisible =
                    loadState.source.refresh is LoadState.Error
                // Show any error that comes from PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    binding.layoutState.tvError.text = "\uD83D\uDE28 Wooops ${it.error}"
                }

            }
        }

        /**
         * 2.7 -> Collect the list of characters and submit it to the adapter
         */
        lifecycleScope.launch {
            viewModel.articles.collectLatest {
                characterAdapter.submitData(it)
            }
        }

    }

    private fun initViews() {
        binding.toolbar.title = "Remote Only"

        binding.layoutState.tvError.text = "Couldn't Load Characters"
        binding.layoutState.tvEmpty.text = "No Character Found"
        binding.layoutState.buttonRetry.setOnClickListener { characterAdapter.refresh() }

        /**
         * 2.9 -> Let's go further and add a footer
         */
        footer.onRetryClicked { characterAdapter.retry() }
        characterAdapter.withLoadStateFooter(footer = footer)

        binding.layoutState.recyclerView.apply {
            setHasFixedSize(true)
            adapter = characterAdapter
        }
        
    }

}