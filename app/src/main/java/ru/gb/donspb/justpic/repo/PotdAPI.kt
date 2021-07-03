package ru.gb.donspb.justpic.repo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.gb.donspb.justpic.model.PODServerResponseData

interface PotdAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String) : Call<PODServerResponseData>
}