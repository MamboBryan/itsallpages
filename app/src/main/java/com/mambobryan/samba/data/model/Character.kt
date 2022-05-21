package com.mambobryan.samba.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.w3c.dom.Comment

/**
 * 2.2 -> Create a Character class with the values you need
 */
@Entity(tableName = "characters")
data class Character(

    @PrimaryKey @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("species") var species: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("created") var created: String? = null

)

val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}

data class Origin(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)

data class Location(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)