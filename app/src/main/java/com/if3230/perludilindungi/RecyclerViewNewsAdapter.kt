package com.if3230.perludilindungi

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.if3230.perludilindungi.Model.News
import com.if3230.perludilindungi.databinding.ItemNewsBinding
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class RecyclerViewNewsAdapter : RecyclerView.Adapter<RecyclerViewNewsAdapter.NewsViewHolder>() {
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
		holder.binding.itemNewsTitle.text = item.title
		val date =
			ZonedDateTime.parse(item.pubDate, DateTimeFormatter.ofPattern("E, d MMM y H:m:s Z"))
		val dateStr = "${date.dayOfMonth} ${date.month} ${date.year} ${date.hour}.${date.minute} ${
			parseTzId(date.zone.toString())
		}"
		holder.binding.itemNewsSubtitle.text = dateStr
		if (item.enclosure._type.startsWith("image")) {
			Glide.with(holder.itemView.context).load(item.enclosure._url)
				.into(holder.binding.itemNewsImg)
		}
		holder.binding.itemNewsUrl.text = item.guid
	}

	override fun getItemCount() = newsList.size

	inner class NewsViewHolder(val binding: ItemNewsBinding) :
		RecyclerView.ViewHolder(binding.root) {
		init {
			binding.root.setOnClickListener { view ->
				val intent = Intent(view.context, NewsWebView::class.java)
				intent.putExtra("url", binding.itemNewsUrl.text as String)
				view.context.startActivity(intent)
			}
		}
	}

	private fun parseTzId(tz: String): String {
		return when (tz) {
			"+07:00" -> "WIB"
			"+08:00" -> "WITA"
			"+09:00" -> "WIT"
			else -> tz
		}
	}
}
