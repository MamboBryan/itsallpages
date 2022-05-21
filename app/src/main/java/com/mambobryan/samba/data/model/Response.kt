package com.mambobryan.samba.data.model

import com.google.firebase.firestore.local.LruGarbageCollector
import com.google.gson.annotations.SerializedName

/**
 * 2.1 -> Create a Response class
 */

data class CharacterResponse(

    @SerializedName("info") var info: ResponseInfo? = ResponseInfo(),
    @SerializedName("results") var characters: ArrayList<Character> = arrayListOf()

)

data class ResponseInfo(

    @SerializedName("count") var count: Int? = null,
    @SerializedName("pages") var pages: Int? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("prev") var prev: String? = null

)