package com.if3230.perludilindungi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.if3230.perludilindungi.database.BookmarkedFaskesDatabase
import com.if3230.perludilindungi.databinding.FragmentBookmarksBinding
import com.if3230.perludilindungi.recycler_view.FaskesAdapter

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class Bookmarks : Fragment() {
	private lateinit var binding: FragmentBookmarksBinding
	private lateinit var viewModel: MainViewModel
	private val adapter = FaskesAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val bookmarkedFaskesDao = BookmarkedFaskesDatabase
			.getInstance(requireContext())
			.bookmarkedFaskesDao()
		val api1 = PerluDilindungiAPI.getInstance()
		viewModel =
			ViewModelProvider(this, MainViewModelFactory(MainRepository(api1), bookmarkedFaskesDao))
				.get(MainViewModel::class.java)
		viewModel.bookmarks.observe(this) {
			adapter.faskesList = it.map { fk -> fk.toFaskes() }.toMutableList()

			Log.i("BookmarksFragment", viewModel.bookmarks.value.toString())
		}
		viewModel.isBookmarksLoaded.observe(this) {
			if (it) {
				binding.faskesBookmarkRecyclerViewProgress.visibility = View.GONE
				binding.faskesBookmarkRecyclerView.visibility = View.VISIBLE
			}
		}
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

		binding.faskesBookmarkRecyclerView.setHasFixedSize(true)
		binding.faskesBookmarkRecyclerView.adapter = adapter

		viewModel.getAllBookmarks()
	}

	override fun onResume() {
		super.onResume()
		val act = requireActivity()
		val topBar: MaterialToolbar = act.findViewById(R.id.content_top_bar)!!
		topBar.title = act.getString(R.string.title_bookmarks_fragment)

		if (viewModel.isBookmarksLoaded.value!!) {
			binding.faskesBookmarkRecyclerViewProgress.visibility = View.GONE
			binding.faskesBookmarkRecyclerView.visibility = View.VISIBLE
		}
	}
}