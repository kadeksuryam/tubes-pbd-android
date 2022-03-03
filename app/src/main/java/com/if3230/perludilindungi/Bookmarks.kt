package com.if3230.perludilindungi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.if3230.perludilindungi.Model.BookmarkedFaskes
import com.if3230.perludilindungi.dao.BookmarkedFaskesDao
import com.if3230.perludilindungi.database.BookmarkedFaskesDatabase
import com.if3230.perludilindungi.databinding.FragmentBookmarksBinding
import com.if3230.perludilindungi.databinding.FragmentNewsBinding

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class Bookmarks : Fragment() {
	private lateinit var binding: FragmentBookmarksBinding
	private lateinit var viewModel: MainViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val bookmarkedFaskesDao = BookmarkedFaskesDatabase
			.getInstance(requireContext())
			.bookmarkedFaskesDao()
		val api1 = PerluDilindungiAPI.getInstance()
		viewModel =
			ViewModelProvider(this, MainViewModelFactory(MainRepository(api1), bookmarkedFaskesDao))
				.get(MainViewModel::class.java)
		viewModel.getAllBookmarks()
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment


		binding = FragmentBookmarksBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		Log.i("BookmarksFragment", viewModel.bookmarks.value.toString())
	}
}