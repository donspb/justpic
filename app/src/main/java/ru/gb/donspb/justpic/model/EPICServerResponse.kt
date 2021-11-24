package ru.gb.donspb.justpic.model

import com.google.gson.annotations.SerializedName

data class EPICServerListResponse(
    val resultsList: List<EPICServerResponse>
)

data class EPICServerResponse(
    @field:SerializedName("identifier") val copyright: String?,
    @field:SerializedName("caption") val date: String?,
    @field:SerializedName("image") val explanation: String?,
    @field:SerializedName("date") val url: String?
)
