package com.mambobryan.samba.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.mambobryan.samba.data.model.Character

@Composable
fun CharactersList(modifier: Modifier, characters: LazyPagingItems<Character>) {

    Column {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (characters.loadState.refresh is LoadState.Loading)
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                        .padding(16.dp, 24.dp)
                )
            if (characters.loadState.refresh is LoadState.Error)
                FloatingActionButton(
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    onClick = { characters.retry() }) {
                    Icon(Icons.Filled.Refresh, "refresh")
                }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = characters, key = { item: Character -> item.id!! }) { value: Character? ->
                value?.let { CharacterItem(character = it) }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (characters.loadState.refresh is LoadState.Loading)
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp, 24.dp)
                        .width(16.dp)
                        .height(16.dp)
                )
            if (characters.loadState.refresh is LoadState.Error)
                FloatingActionButton(
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    onClick = { characters.retry() }
                ) {
                    Icon(Icons.Filled.Refresh, "refresh")
                }
        }

    }


}

sealed class ItemsState {
    object Loading : ItemsState()
    data class Empty(val message: String) : ItemsState()
    data class Error(val message: String) : ItemsState()
}

@Composable
fun CharactersState(characters: LazyPagingItems<Character>, state: ItemsState?) {

    if (state != null)
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (state is ItemsState.Empty || state is ItemsState.Error)
                Text(text = "Error/Empty")
            if (state is ItemsState.Loading)
                CircularProgressIndicator(modifier = Modifier.padding(16.dp, 24.dp))
            if (state is ItemsState.Error)
                FloatingActionButton(
                    onClick = { characters.refresh() }) {
                    Icon(Icons.Filled.Refresh, "refresh")
                }

        }

}