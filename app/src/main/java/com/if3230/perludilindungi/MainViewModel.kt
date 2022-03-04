package com.if3230.perludilindungi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.if3230.perludilindungi.Model.*
import com.if3230.perludilindungi.dao.BookmarkedFaskesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(
	private val repository: MainRepository,
	private val bookmarkedFaskesDao: BookmarkedFaskesDao
) : ViewModel() {
	val checkInStatus = MutableLiveData<CheckInResponse>()
	val news = MutableLiveData<NewsResponse>()
	val province = MutableLiveData<ProvinceResponse>()
	val finishLoadingProvince = MutableLiveData(false)
	val city = MutableLiveData<CityResponse>()
	val finishLoadingCity = MutableLiveData(false)
	val faskes = MutableLiveData<FaskesResponse>()
	val finishLoadingFaskes = MutableLiveData(false)
	val errorMessage = MutableLiveData<String>()
	val bookmarks = MutableLiveData<MutableList<BookmarkedFaskes>>()
	val finishLoadingNews = MutableLiveData(false)
	val isBookmarksLoaded = MutableLiveData(false)

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

	fun getProvince() {
		viewModelScope.launch {
			val response = withContext(Dispatchers.IO) {
				val response = repository.getProvince()
				response
			}
			if (response.isSuccessful) {
				province.value = response.body()
			} else {
				errorMessage.value = response.message()
			}
			finishLoadingProvince.value = true
		}
	}

	fun getCity(startId: String) {
		viewModelScope.launch {
			val response = withContext(Dispatchers.IO){
				val response = repository.getCity(startId)
				response
			}
			if (response.isSuccessful){
				city.value = response.body()
			}else{
				errorMessage.value = response.message()
			}
			finishLoadingCity.value = true
		}
	}

	fun getFaskes(province: String, city: String) {
		viewModelScope.launch {
			val response = withContext(Dispatchers.IO){
				val response = repository.getFaskes(province, city)
				response
			}
			if (response.isSuccessful){
				faskes.value = response.body()
			}else{
				errorMessage.value = response.message()
			}
			finishLoadingFaskes.value = true
		}
	}

	fun getAllBookmarks() {
		viewModelScope.launch {
			val allBookmarks = withContext(Dispatchers.IO) {
				bookmarkedFaskesDao.getAll()
			}

			bookmarks.value = allBookmarks.toMutableList()
			isBookmarksLoaded.value = true
		}
	}

	fun bookmark(faskes: BookmarkedFaskes) {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				bookmarkedFaskesDao.insert(faskes)
			}

			if (!isBookmarksLoaded.value!!) {
				getAllBookmarks()
			}

			val bm = bookmarks.value!!
			bm.add(faskes)
			bookmarks.value = bm
		}
	}

	fun unbookmark(faskes: BookmarkedFaskes) {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				bookmarkedFaskesDao.delete(faskes)
			}

			if (!isBookmarksLoaded.value!!) {
				getAllBookmarks()
			}

			val bm = bookmarks.value!!
			bm.remove(faskes)
			bookmarks.value = bm
		}
	}

}