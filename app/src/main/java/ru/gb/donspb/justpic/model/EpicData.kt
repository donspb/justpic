package ru.gb.donspb.justpic.model

sealed class EpicData {
    data class Success(val serverResponseData: EPICServerResponse) : EpicData()
    data class Error(val error: Throwable) : EpicData()
    data class Loading(val progress: Int?) : EpicData()
}