package com.mambobryan.samba.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mambobryan.samba.data.model.NOTE_COMPARATOR
import com.mambobryan.samba.data.model.Note
import com.mambobryan.samba.databinding.ItemCharacterBinding
import com.mambobryan.samba.databinding.ItemNoteBinding
import javax.inject.Inject

class NoteAdapter @Inject constructor() :
    PagingDataAdapter<Note, NoteAdapter.NoteViewHolder>(NOTE_COMPARATOR) {

    class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {

            val number = (absoluteAdapterPosition + 1)
            binding.apply {
                tvNoteDetail.text = note.description
                tvNoteNumber.text = number.toString()
            }
        }

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val character = getItem(position)
        character?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

}