package com.mambobryan.samba.ui.notes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.mambobryan.samba.R
import com.mambobryan.samba.databinding.FragmentTemplateBinding
import com.mambobryan.samba.ui.adapters.ItemLoadStateAdapter
import com.mambobryan.samba.ui.adapters.NoteAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class FirestoreFragment : Fragment(R.layout.fragment_template) {

    private val binding by viewBinding(FragmentTemplateBinding::bind)
    private val viewModel: NotesViewModel by activityViewModels()

    @Inject
    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var footer: ItemLoadStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        lifecycleScope.launch {
            noteAdapter.loadStateFlow.collectLatest { loadState ->

                Timber.i("Load state => $loadState")

                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && noteAdapter.itemCount == 0
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
                // Show any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    binding.layoutState.tvError.text = "\uD83D\uDE28 Wooops ${it.error}"
                }

            }
        }

        lifecycleScope.launch {
            viewModel.fireStoreNotes.collectLatest {
                noteAdapter.submitData(it)
            }
        }

    }

    private fun initViews() {
        binding.toolbar.title = "Remote Firestore"

        binding.layoutState.tvError.text = "Couldn't Load Notes"
        binding.layoutState.tvEmpty.text = "No Note Found"
        binding.layoutState.buttonRetry.setOnClickListener { noteAdapter.refresh() }

        footer.onRetryClicked { noteAdapter.retry() }

        noteAdapter.withLoadStateFooter(footer = footer)

        binding.layoutState.recyclerView.apply {
            setHasFixedSize(true)
            adapter = noteAdapter
        }
        
    }

}