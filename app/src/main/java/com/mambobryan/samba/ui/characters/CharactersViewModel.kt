package com.mambobryan.samba.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mambobryan.samba.data.repositories.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 2.7 -> Create a viemodel class and get the characters
 */
@HiltViewModel
class CharactersViewModel @Inject constructor(
    repository: CharactersRepository
) : ViewModel(){

    val articles = repository.getCharacters().cachedIn(viewModelScope)

    val articlesWithCache = repository.getCharactersWithCache().cachedIn(viewModelScope)

}