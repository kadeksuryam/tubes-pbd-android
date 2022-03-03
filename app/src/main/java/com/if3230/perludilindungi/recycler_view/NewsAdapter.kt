package com.if3230.perludilindungi.recycler_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.if3230.perludilindungi.Model.News
import com.if3230.perludilindungi.databinding.ItemNewsBinding
import com.if3230.perludilindungi.fragment.NewsWebView
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class NewsAdapter(private val oldFragment: Fragment) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
	private var _newsList = mutableListOf<News>()
	var newsList
		get() = _newsList
		@SuppressLint("NotifyDataSetChanged")
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
		holder.binding.itemNewsTitle.text = item.title
		val date =
			ZonedDateTime.parse(item.pubDate, DateTimeFormatter.ofPattern("E, d MMM y H:m:s Z"))
		val dateStr = "${date.dayOfMonth} ${date.month} ${date.year} ${date.hour}.${date.minute.toString().padStart(2, '0')} ${
			parseTzId(date.zone.toString())
		}"
		holder.binding.itemNewsSubtitle.text = dateStr
		if (item.enclosure._type.startsWith("image")) {
			Glide.with(holder.itemView.context).load(item.enclosure._url)
				.into(holder.binding.itemNewsImg)
		}

		holder.binding.root.setOnClickListener { view ->
			val newsWebView = NewsWebView.newInstance(item.guid)
			val fragmentManager = oldFragment.parentFragmentManager
			val transaction = fragmentManager.beginTransaction()
			transaction.replace(oldFragment.id, newsWebView)
			transaction.addToBackStack(oldFragment::class.java.toString())
			transaction.commit()
		}
	}

	override fun getItemCount() = newsList.size

	inner class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

	private fun parseTzId(tz: String): String {
		return when (tz) {
			"+07:00" -> "WIB"
			"+08:00" -> "WITA"
			"+09:00" -> "WIT"
			else -> tz
		}
	}
}
