package com.mambobryan.samba.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 4.2 -> Create a new class to store the keys
 */
@Entity(tableName = "character_keys")
data class CharacterKeys(
    @PrimaryKey
    val characterId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)