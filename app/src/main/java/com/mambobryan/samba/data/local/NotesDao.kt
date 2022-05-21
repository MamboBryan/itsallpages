package com.mambobryan.samba.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.mambobryan.samba.data.model.Character
import com.mambobryan.samba.data.model.Note

/**
 * 1.2 -> Create your entity's Data Access Object (DAO)
 */
@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note:Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notes: List<Note>)

    @Query("DELETE FROM notes WHERE id IS :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()

    /**
     * 1.3 -> Notice that Room can return a PagingSource, isn't that amazing?
     */
    @Query("SELECT * FROM notes")
    fun getAllNotes(): PagingSource<Int, Note>

    @Delete
    suspend fun delete(note: Note)

}