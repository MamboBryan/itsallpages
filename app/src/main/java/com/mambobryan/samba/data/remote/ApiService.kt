package com.mambobryan.samba.data.remote

import com.mambobryan.samba.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 2.3 -> Setup your API service to return the custom response class
 */
interface ApiService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterResponse

}