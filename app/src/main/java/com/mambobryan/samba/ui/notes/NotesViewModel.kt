package com.mambobryan.samba.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mambobryan.samba.data.model.Note
import com.mambobryan.samba.data.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    repository: NoteRepository
) : ViewModel() {

    /**
     * 1.5 -> Get all notes from repository
     */
    val roomNotes: Flow<PagingData<Note>> =
        repository.getRoomNotes().cachedIn(viewModelScope)

    val fireStoreNotes =
        repository.getFirestoreNotes().cachedIn(viewModelScope)

}