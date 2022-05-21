package com.mambobryan.samba.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.mambobryan.samba.data.model.Note
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import timber.log.Timber

/**
 * 3.2 -> Create a custom paging source class and extend the paging library
 *        PagingSource class
 */
class NotesPagingSource(
    private val collection: CollectionReference
) : PagingSource<DocumentSnapshot, Note>() {

    override fun getRefreshKey(state: PagingState<DocumentSnapshot, Note>): DocumentSnapshot? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<DocumentSnapshot>): LoadResult<DocumentSnapshot, Note> {

        val document = params.key

        return try {

            val query = collection.orderBy("date", Query.Direction.DESCENDING).limit(20)

            val documents =
                if (document == null) query.get().await()
                else query.startAfter(document).get().await()

            val nextKey = if (documents.isEmpty) null else documents.last()
//            val nextKey = documents.lastOrNull()

            val notes = documents.toObjects(Note::class.java)

            delay(3000)

            Timber.i("Loaded Items Size => ${documents.size()}")

            LoadResult.Page(data = notes, null, nextKey = nextKey)

        } catch (exception: FirebaseFirestoreException) {
            return LoadResult.Error(exception)
        }

    }
}