package com.mambobryan.samba.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mambobryan.samba.data.model.Character
import com.mambobryan.samba.utils.Constants
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

/**
 * 2.4 -> Create a custom paging source class and extend the paging library
 *        PagingSource class
 */
class CharactersPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        // Get the current page from the params
        val page = params.key ?: Constants.START_PAGE

        return try {
            // Get the response from the server
            val response = apiService.getCharacters(page)

            // Get the list of characters in the page
            val characters = response.characters

            // Get the next key for loading the next page
            val nextKey = if(response.info?.next.isNullOrBlank()) null else page + 1
            // val nextKey = if (articles.isEmpty()) null else page + 1

            // Get the previous key
            val previousKey = if (page == Constants.START_PAGE) null else page - 1

            delay(3000)

            // Return the LoadResult with characters, previousKey and nextKey
            LoadResult.Page(data = characters, prevKey = previousKey, nextKey = nextKey)

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }
}