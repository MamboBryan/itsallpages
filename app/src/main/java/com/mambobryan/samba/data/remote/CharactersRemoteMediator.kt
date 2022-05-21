package com.mambobryan.samba.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mambobryan.samba.data.local.AppDatabase
import com.mambobryan.samba.data.model.Character
import com.mambobryan.samba.data.model.CharacterKeys
import com.mambobryan.samba.utils.Constants.START_PAGE
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val service: ApiService,
    private val database: AppDatabase
) : RemoteMediator<Int, Character>() {

    init {
        Timber.i("Remote Mediator called!")
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.PREPEND -> {
                Timber.i("Remote Mediator prepend!")
                val articleKeys = getCharacterKeysForFirstItem(state)
                val prevKey = articleKeys?.prevKey
                if (prevKey == null) return MediatorResult.Success(endOfPaginationReached = articleKeys != null)
                prevKey
            }
            LoadType.REFRESH -> {
                Timber.i("Remote Mediator refresh!")
                val articleKeys = getCharacterKeysClosestToCurrentPosition(state)
                val nextKey = articleKeys?.nextKey
                if (nextKey == null) START_PAGE else nextKey
            }
            LoadType.APPEND -> {
                Timber.i("Remote Mediator append!")
                val articleKeys = getCharacterKeysForLastItem(state)
                val nextKey = articleKeys?.nextKey
                if (nextKey == null) return MediatorResult.Success(endOfPaginationReached = articleKeys != null)
                nextKey
            }
        }

        try {

            Timber.i("Getting articles")

            val response = service.getCharacters(page)

            val characters = response.characters

            val isEndOfPagination = response.info?.next.isNullOrBlank()
//            val isEndOfPagination = articles.isEmpty()

            database.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    database.characterKeysDao().deleteAll()
                    database.charactersDao().deleteAll()
                }

                val prevKey = if (page == START_PAGE) null else page - 1
                val nextKey = if (isEndOfPagination) null else page + 1

                val keys =
                    characters.map { it.id?.let { id -> CharacterKeys(id, prevKey, nextKey) } }

                database.characterKeysDao().insert(keys as List<CharacterKeys>)
                database.charactersDao().insert(characters)

            }

            return MediatorResult.Success(endOfPaginationReached = isEndOfPagination)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getCharacterKeysForFirstItem(state: PagingState<Int, Character>): CharacterKeys? {

        val pagingSource = state.pages.firstOrNull()

        val characters = pagingSource?.data

        if (characters.isNullOrEmpty()) return null

        val firstCharacter = characters.firstOrNull()

        if (firstCharacter == null) return null

        val keys = database.characterKeysDao().getCharacterKeysByCharacterId(firstCharacter.id!!)

        Timber.i("Character Key for First Item => $keys")

        return keys

//        ----------------------------------------------------------------------------------------------
//        09 -> A oneliner for all we've done above. THIS IS KOTLIN!!!
//        ----------------------------------------------------------------------------------------------
//        return state.pages
//            .lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let { article ->
//                database.articleKeysDao().getCharacterKeysByCharacterId(article.id)
//            }

    }

    private suspend fun getCharacterKeysClosestToCurrentPosition(state: PagingState<Int, Character>): CharacterKeys? {

        val anchorPosition = state.anchorPosition

        if (anchorPosition == null) return null

        val closestItem = state.closestItemToPosition(anchorPosition)

        if (closestItem == null) return null

        val keys = database.characterKeysDao().getCharacterKeysByCharacterId(closestItem.id!!)

        Timber.i("Character Key for Closest Item => $keys")

        return keys

    }

    private suspend fun getCharacterKeysForLastItem(state: PagingState<Int, Character>): CharacterKeys? {

        val pagingSource = state.pages.lastOrNull()

        val characters = pagingSource?.data

        if (characters.isNullOrEmpty()) return null

        val lastCharacter = characters.lastOrNull()

        if (lastCharacter == null) return null

        val keys = database.characterKeysDao().getCharacterKeysByCharacterId(lastCharacter.id!!)

        Timber.i("Character Key for Last Item => $keys")

        return keys

//  ----------------------------------------------------------------------------------------------
//        10 -> A oneliner for all we've done above. NO WAYYYY!
//  ----------------------------------------------------------------------------------------------
//        return state.pages
//            .lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let { article ->
//                database.articleKeysDao().getCharacterKeysByCharacterId(article.id)
//            }

    }
}