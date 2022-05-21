package com.mambobryan.samba.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mambobryan.samba.data.local.AppDatabase
import com.mambobryan.samba.data.model.Character
import com.mambobryan.samba.data.remote.ApiService
import com.mambobryan.samba.data.remote.CharactersPagingSource
import com.mambobryan.samba.data.remote.CharactersRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 2.5 -> Create a character repository passing the ApiService class
 */

class CharactersRepository @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase
) {

    companion object {
        const val PAGE_SIZE = 20
    }

    /**
     * 2.6 -> Create a pager that return the paging data stream
     */
    // REMOTE ONLY
    fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { CharactersPagingSource(apiService) }
        ).flow
    }

    // REMOTE WITH CACHE
    @OptIn(ExperimentalPagingApi::class)
    fun getCharactersWithCache(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = CharactersRemoteMediator(service = apiService, database = database),
            pagingSourceFactory = { database.charactersDao().getAllCharacters() }
        ).flow
    }

}