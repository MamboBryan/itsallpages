package com.mambobryan.samba.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.mambobryan.samba.data.model.Character

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: List<Character>)

    @Query("DELETE FROM characters WHERE id IS :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM characters")
    suspend fun deleteAll()

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): PagingSource<Int, Character>

    @Delete
    suspend fun delete(character: Character)

}