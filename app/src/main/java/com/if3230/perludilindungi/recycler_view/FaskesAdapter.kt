package com.if3230.perludilindungi.recycler_view

import android.annotation.SuppressLint
import android.util.Log
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
			Log.i("FaskesAdapter", _faskesList.toString())
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaskesAdapter.FaskesHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = ItemFaskesBinding.inflate(inflater, parent, false)
		Log.i("FaskesAdapter", "onCreateViewHolder")
		return FaskesHolder(binding)
	}

	override fun onBindViewHolder(holder: FaskesAdapter.FaskesHolder, position: Int) {
		val item = _faskesList[position]
		holder.binding.itemFaskesName.text = item.nama
		holder.binding.itemFaskesAddress.text = item.alamat
		holder.binding.itemFaskesKode.text = item.kode
		holder.binding.itemFaskesTelp.text = item.telp
		holder.binding.itemFaskesType.text = item.jenis_fakses
		Log.i("FaskesAdapter", "onBindViewHolder")
		holder.binding.root.setOnClickListener {
			Toast.makeText(holder.binding.root.context, item.nama, Toast.LENGTH_SHORT).show()
		}
	}

	override fun getItemCount() = faskesList.size

	inner class FaskesHolder(val binding: ItemFaskesBinding) : RecyclerView.ViewHolder(binding.root)
}
