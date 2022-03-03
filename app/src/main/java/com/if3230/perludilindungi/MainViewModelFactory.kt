package com.if3230.perludilindungi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.if3230.perludilindungi.dao.BookmarkedFaskesDao

class MainViewModelFactory constructor(
	private val repository: MainRepository,
	private val bookmarkedFaskesDao: BookmarkedFaskesDao
) :
	ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
			MainViewModel(this.repository, this.bookmarkedFaskesDao) as T
		} else {
			throw IllegalArgumentException("ViewModel Not Found")
		}
	}
}