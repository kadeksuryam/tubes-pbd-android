package com.if3230.perludilindungi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.if3230.perludilindungi.Model.CheckInRequest
import com.if3230.perludilindungi.Model.CheckInResponse
import com.if3230.perludilindungi.Model.NewsResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {
	val checkInStatus = MutableLiveData<CheckInResponse>()
	val news = MutableLiveData<NewsResponse>()
	val errorMessage = MutableLiveData<String>()
	val finishLoadingNews = MutableLiveData(false)

	fun doCheckIn(checkInRequest: CheckInRequest) {
		val response = repository.doCheckIn(checkInRequest)
		response.enqueue(object : Callback<CheckInResponse> {
			override fun onResponse(
				call: Call<CheckInResponse>,
				response: Response<CheckInResponse>
			) {
				checkInStatus.postValue(response.body())
			}

			override fun onFailure(call: Call<CheckInResponse>, t: Throwable) {
				errorMessage.postValue(t.message)
			}
		})
	}

	fun getNews() {
		viewModelScope.launch {
			val response = withContext(Dispatchers.IO) {
				val response = repository.getNews()
				response
			}

			if (response.isSuccessful) {
				news.value = response.body()
			} else {
				errorMessage.value = response.message()
			}
			finishLoadingNews.value = true
		}
	}
}