package com.mambobryan.samba.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mambobryan.samba.data.local.AppDatabase
import com.mambobryan.samba.data.remote.NotesPagingSource
import javax.inject.Inject

/**
 * 1.4 -> Create a repository
 */

class NoteRepository @Inject constructor(
    private val database: AppDatabase
) {

    private val collections = Firebase.firestore.collection("notes")

    /**
     * 1.4 -> Create a pager for local notes
     */

    fun getRoomNotes() = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { database.notesDao().getAllNotes() }
    ).flow

    /**
     * 3.3 -> Create a pager for firestore notes
     */
    fun getFirestoreNotes() = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { NotesPagingSource(collections) }
    ).flow

/*    suspend fun generateFirestoreNotes(){
        withContext(Dispatchers.IO){

            val notes = mutableListOf<Note>()

            for (number in 1 until 101){
                val note = Note( description = "Firestore Note $number", date = Date())
                notes.add(note)
            }

            notes.forEach {
                collections.add(it)
            }

        }
    }*/

}