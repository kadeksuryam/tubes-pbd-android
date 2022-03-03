package com.if3230.perludilindungi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.if3230.perludilindungi.databinding.FragmentNewsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [News.newInstance] factory method to
 * create an instance of this fragment.
 */
class News : Fragment() {
	private lateinit var binding: FragmentNewsBinding
	private lateinit var viewModel: MainViewModel
	private var perluDilindungiAPI = PerduliLindungiAPI.getInstance()
	private val adapter = RecyclerViewNewsAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel =
			ViewModelProvider(this, MainViewModelFactory(MainRepository(perluDilindungiAPI)))
				.get(MainViewModel::class.java)

		viewModel.news.observe(this) {
			adapter.newsList = it.results.toMutableList()
		}
		viewModel.errorMessage.observe(this) {
			Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
		}
		viewModel.finishLoadingNews.observe(this) {
			if (it) {
				binding.newsRecyclerViewProgress.visibility = View.GONE
				binding.newsRecyclerView.visibility = View.VISIBLE
			}
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		binding = FragmentNewsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.newsRecyclerView.setHasFixedSize(true)
		binding.newsRecyclerView.adapter = adapter

		if (adapter.itemCount == 0) {
			viewModel.getNews()
		}
	}

	override fun onResume() {
		super.onResume()
		val act = requireActivity()
		val topBar: MaterialToolbar = act.findViewById(R.id.content_top_bar)!!
		topBar.title = act.getString(R.string.title_news_fragment)
	}
}