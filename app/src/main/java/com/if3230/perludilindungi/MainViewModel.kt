package com.if3230.perludilindungi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
	private var job: Job? = null

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
		job = CoroutineScope(Dispatchers.IO).launch {
			val response = repository.getNews()
			withContext(Dispatchers.Main) {
				if (response.isSuccessful) {
					news.postValue(response.body())
				} else {
					errorMessage.postValue(response.message())
				}
				finishLoadingNews.postValue(true)
			}
		}
	}

	override fun onCleared() {
		super.onCleared()
		job?.cancel()
	}
}