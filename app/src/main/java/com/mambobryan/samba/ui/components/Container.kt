package com.mambobryan.samba.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mambobryan.samba.data.model.Character
import com.mambobryan.samba.ui.characters.CharactersViewModel

@Composable
fun CharactersScreen(
    navController: NavController,
    viewModel: CharactersViewModel
) {

    val characters = viewModel.articlesWithCache.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Sweet Compose") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface
            )
        },
        content = {
            CharactersContent(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                characters = characters
            )
        }
    )
}

@Composable
fun CharactersContent(modifier: Modifier, characters: LazyPagingItems<Character>) {

    val loadState = characters.loadState

    val errorState = loadState.source.append as? LoadState.Error
        ?: loadState.source.prepend as? LoadState.Error
        ?: loadState.append as? LoadState.Error
        ?: loadState.prepend as? LoadState.Error

    val isEmptyVisible = remember {
        loadState.refresh is LoadState.NotLoading && characters.itemCount == 0
    }
    val isContentVisible = remember {
        loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
    }
    val isLoadingVisible = remember {
        loadState.mediator?.refresh is LoadState.Loading
    }

    var error = remember {
        errorState?.let { "\uD83D\uDE28 Wooops ${it.error}" }
    }

    val state = remember {
        when {
            isEmptyVisible -> ItemsState.Empty("No Characters Found")
            isContentVisible -> ItemsState.Error("Couldn't Load Characters")
            isLoadingVisible -> ItemsState.Loading
            else -> null
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CharactersList(modifier = modifier.fillMaxSize(), characters = characters)
    }

}

