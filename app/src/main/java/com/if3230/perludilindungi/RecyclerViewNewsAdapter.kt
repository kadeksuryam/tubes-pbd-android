package com.if3230.perludilindungi

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.if3230.perludilindungi.Model.News
import com.if3230.perludilindungi.databinding.ItemNewsBinding

class RecyclerViewNewsAdapter() : RecyclerView.Adapter<RecyclerViewNewsAdapter.NewsViewHolder>() {
	private var _newsList = mutableListOf<News>()
	var newsList
		get() = _newsList
		set(value) {
			_newsList = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = ItemNewsBinding.inflate(inflater, parent, false)
		return NewsViewHolder(binding)
	}

	override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
		val item = _newsList[position]
		holder.binding.tvName.text = item.title
		holder.binding.tvPrice.text = item.pubDate
	}

	override fun getItemCount() = newsList.size

	inner class NewsViewHolder (val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
		init {
			binding.root.setOnClickListener {
				Toast.makeText(binding.root.context, binding.tvName.text, Toast.LENGTH_SHORT).show()
			}
		}
	}
}
