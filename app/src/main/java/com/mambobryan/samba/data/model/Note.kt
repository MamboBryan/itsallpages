package com.mambobryan.samba.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import java.util.*

/**
 * 1.1 -> Create an Entity Class
 */
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) @Exclude var id: Int = 0,
    @DocumentId @Ignore var docId: String? = "",
    @Ignore var date: Date? = null,
    var description: String? = ""
)

val NOTE_COMPARATOR = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}