package ru.gb.donspb.justpic.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.donspb.justpic.repo.EPICRetrofitImpl

class AddonViewModel (
    private val liveDataToObserve: MutableLiveData<EpicData> = MutableLiveData(),
    private val retrofitImpl: EPICRetrofitImpl = EPICRetrofitImpl()
    ) : ViewModel() {

    fun getData(): LiveData<EpicData> {
        sendServerRequest()
        return liveDataToObserve
    }

    private fun sendServerRequest() {
        liveDataToObserve.value = EpicData.Loading(null)
        retrofitImpl.getEpicRetrofitImpl().getEpicArray().enqueue(
            object : Callback<EPICServerResponse> {

                override fun onResponse(
                    call: Call<EPICServerResponse>,
                    response: Response<EPICServerResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataToObserve.value = EpicData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataToObserve.value = EpicData.Error(Throwable("Udefined error"))
                        } else {
                            liveDataToObserve.value = EpicData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<EPICServerResponse>, t: Throwable) {
                    liveDataToObserve.value = EpicData.Error(t)
                }
            })
    }
}