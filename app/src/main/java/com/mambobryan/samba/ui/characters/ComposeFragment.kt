package com.mambobryan.samba.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.composethemeadapter.MdcTheme
import com.mambobryan.samba.ui.components.CharactersScreen

class ComposeFragment : Fragment() {

    private val viewModel: CharactersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    CharactersScreen(findNavController(), viewModel)
                }
            }
        }
    }

}