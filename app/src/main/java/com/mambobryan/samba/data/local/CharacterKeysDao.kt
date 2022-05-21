package com.mambobryan.samba.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mambobryan.samba.data.model.CharacterKeys

@Dao
interface CharacterKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<CharacterKeys>)

    @Query("SELECT * FROM character_keys WHERE characterId = :characterId")
    suspend fun getCharacterKeysByCharacterId(characterId: Int): CharacterKeys?

    @Query("DELETE FROM character_keys")
    suspend fun deleteAll()

}