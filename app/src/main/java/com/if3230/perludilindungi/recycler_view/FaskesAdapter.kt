package com.if3230.perludilindungi.recycler_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.if3230.perludilindungi.Model.Faskes
import com.if3230.perludilindungi.databinding.ItemFaskesBinding

class FaskesAdapter : RecyclerView.Adapter<FaskesAdapter.FaskesHolder>() {
	private var _faskesList = mutableListOf<Faskes>()
	var faskesList
		get() = _faskesList
		@SuppressLint("NotifyDataSetChanged")
		set(value) {
			_faskesList = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaskesAdapter.FaskesHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = ItemFaskesBinding.inflate(inflater, parent, false)
		return FaskesHolder(binding)
	}

	override fun onBindViewHolder(holder: FaskesAdapter.FaskesHolder, position: Int) {
		val item = _faskesList[position]
		val name = item.nama.titlecase()
		holder.binding.itemFaskesName.text = name
		holder.binding.itemFaskesAddress.text = item.alamat
		holder.binding.itemFaskesCode.text = item.kode
		holder.binding.itemFaskesPhone.text = item.telp
		var type = item.jenis_fakses.titlecase()
		if (type == "") {
			type = name.split(" ")[0]
		}

		// Change color for faskes label
		/*
		val typeUC = type.uppercase()
		val ctx = holder.binding.root.context
		val valueColor = TypedValue()
		when (typeUC) {
			JenisFaskes.KLINIK.toString() -> ctx
				.theme.resolveAttribute(
					R.color.md_theme_light_primaryContainer,
					valueColor,
					true
				)
			JenisFaskes.PUSKESMAS.toString() -> ctx
				.theme.resolveAttribute(
					R.color.md_theme_light_errorContainer,
					valueColor,
					true
				)
			JenisFaskes.FKTP.toString() -> ctx
				.theme.resolveAttribute(
					R.color.md_theme_light_secondaryContainer,
					valueColor,
					true
				)
		}
		 */

		holder.binding.itemFaskesType.text = type
		holder.binding.root.setOnClickListener {
			Toast.makeText(holder.binding.root.context, name, Toast.LENGTH_SHORT).show()
		}
	}

	override fun getItemCount() = faskesList.size

	inner class FaskesHolder(val binding: ItemFaskesBinding) : RecyclerView.ViewHolder(binding.root)
}

private fun String.titlecase(): String {
	val words = this.split(' ')
	return words.joinToString(" ") { word ->
		word.lowercase().replaceFirstChar { c ->
			c.uppercase()
		}
	}
}
