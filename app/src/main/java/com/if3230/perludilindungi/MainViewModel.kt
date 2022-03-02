package com.if3230.perludilindungi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.if3230.perludilindungi.Model.NewsResponse
import com.if3230.perludilindungi.Model.CheckInRequest
import com.if3230.perludilindungi.Model.CheckInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {
    val checkInStatus = MutableLiveData<CheckInResponse>()
    val news = MutableLiveData<NewsResponse>()
    val errorMessage = MutableLiveData<String>()
    val finishLoadingNews = MutableLiveData(false)

    fun doCheckIn(checkInRequest: CheckInRequest) {
        val response = repository.doCheckIn(checkInRequest)
        response.enqueue(object : Callback<CheckInResponse> {
            override fun onResponse(call: Call<CheckInResponse>, response: Response<CheckInResponse>) {
                checkInStatus.postValue(response.body())
            }
            override fun onFailure(call: Call<CheckInResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getNews() {
        val response = repository.getNews()
	    response.enqueue(object : Callback<NewsResponse> {
	        override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                news.postValue(response.body())
                finishLoadingNews.postValue(true)
	        }
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
                finishLoadingNews.postValue(true)
            }
        })
    }
}