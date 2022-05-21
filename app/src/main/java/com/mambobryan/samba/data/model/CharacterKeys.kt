package com.mambobryan.samba.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_keys")
data class CharacterKeys(
    @PrimaryKey
    val characterId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)