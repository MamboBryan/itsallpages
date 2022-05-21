package com.mambobryan.samba.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.mambobryan.samba.data.model.CHARACTER_COMPARATOR
import com.mambobryan.samba.data.model.Character
import com.mambobryan.samba.databinding.ItemCharacterBinding
import com.mambobryan.samba.utils.toDate
import org.ocpsoft.prettytime.PrettyTime
import java.util.*
import javax.inject.Inject

class CharacterAdapter @Inject constructor() :
    PagingDataAdapter<Character, CharacterAdapter.CharacterViewHolder>(CHARACTER_COMPARATOR) {

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            val created = character.created.toDate()
            val shimmer = Shimmer.AlphaHighlightBuilder()
                .setDuration(1800)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.6f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()

            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }

            val prettyTime = PrettyTime(Locale.getDefault())
            val ago: String = prettyTime.format(created)

            binding.apply {
                ivCharacterImage.load(character.image) {
                    placeholder(shimmerDrawable)
                    crossfade(true)
                    scale(Scale.FILL)
                }
                tvCharacterName.text = character.name
                tvCharacterNumber.text = (absoluteAdapterPosition + 1).toString()
                tvCharacterDetails.text = "${character.species} \u2022 ${character.gender}"
            }
        }

    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        character?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

}