package com.if3230.perludilindungi.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_faskes")
data class BookmarkedFaskes(
	@PrimaryKey(autoGenerate = true)
	val _id: Int,
	val kode: String,
	val nama: String,
	val telp: String?,
	val alamat: String,
	val kota: String,
	val provinsi: String,
	val latitude: String?,
	val longitude: String?,
	@ColumnInfo(name = "jenis_faskes")
	val jenisFaskes: String?,
	val status: String?,
) {
	override fun equals(other: Any?): Boolean = (other is BookmarkedFaskes)
			&& other.kode == this.kode

	override fun hashCode(): Int {
		var result = kode.hashCode()
		result = 31 * result + nama.hashCode()
		result = 31 * result + telp.hashCode()
		result = 31 * result + alamat.hashCode()
		result = 31 * result + kota.hashCode()
		result = 31 * result + provinsi.hashCode()
		result = 31 * result + latitude.hashCode()
		result = 31 * result + longitude.hashCode()
		result = 31 * result + jenisFaskes.hashCode()
		result = 31 * result + status.hashCode()
		return result
	}

	fun toFaskes(): Faskes {
		return Faskes(
			null,
			kode,
			nama,
			kota,
			provinsi,
			alamat,
			latitude,
			longitude,
			telp,
			jenisFaskes,
			null,
			status,
			null,
			null
		)
	}
}
