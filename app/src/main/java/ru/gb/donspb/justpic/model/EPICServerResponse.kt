package ru.gb.donspb.justpic.model

import com.google.gson.annotations.SerializedName

data class EPICServerListResponse(
    val resultsList: List<EPICServerResponse>
)

data class EPICServerResponse(
    @field:SerializedName("identifier") val id: String?,
    @field:SerializedName("caption") val caption: String?,
    @field:SerializedName("image") val url: String?,
    @field:SerializedName("date") val date: String?
)
