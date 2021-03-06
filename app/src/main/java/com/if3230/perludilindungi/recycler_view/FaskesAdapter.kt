package com.if3230.perludilindungi.recycler_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.if3230.perludilindungi.Model.Faskes
import com.if3230.perludilindungi.databinding.ItemFaskesBinding
import com.if3230.perludilindungi.fragment.DetailFaskes

class FaskesAdapter(private val oldFragment: Fragment) :
	RecyclerView.Adapter<FaskesAdapter.FaskesHolder>() {
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
		val type = if (item.jenis_faskes == "" || item.jenis_faskes == null) {
			"-"
		} else {
			item.jenis_faskes.titlecase()
		}

		holder.binding.itemFaskesType.text = type
		holder.binding.root.setOnClickListener {
			val faskesDetail = DetailFaskes.newInstance(
				item.nama,
				item.kode,
				item.jenis_faskes,
				item.alamat,
				item.kota,
				item.provinsi,
				item.telp,
				item.status,
				item.latitude,
				item.longitude
			)
			val fragmentManager = oldFragment.parentFragmentManager
			val transaction = fragmentManager.beginTransaction()
			transaction.replace(oldFragment.id, faskesDetail)
			transaction.addToBackStack(oldFragment::class.java.toString())
			transaction.setReorderingAllowed(true)
			transaction.commit()
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
