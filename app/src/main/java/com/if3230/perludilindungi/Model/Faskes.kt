package com.if3230.perludilindungi.Model

enum class JenisFaskes {
	RUMAH_SAKIT {
		override fun toString(): String {
			return "RUMAH SAKIT"
		}
	},
	NULL {
		override fun toString(): String {
			return ""
		}
	},
	KLINIK,
	SENTRA,
	FKTP,
	PUSKESMAS,
}

enum class StatusFaskes {
	SIAP_VAKSINASI {
		override fun toString(): String {
			return "Siap Vaksinasi"
		}
	}
}

data class DetailFaskes(
	val id: Int,
	val kode: String,
	val batch: String,
	val divaksin: Int,
	val divaksin_1: Int,
	val divaksin_2: Int,
	val batal_vaksin: Int,
	val batal_vaksin_1: Int,
	val batal_vaksin_2: Int,
	val pending_vaksin: Int,
	val pending_vaksin_1: Int,
	val pending_vaksin_2: Int,
	val tanggal: String,
)

data class Faskes(
	val id: Int?,
	val kode: String,
	val nama: String,
	val kota: String,
	val provinsi: String,
	val alamat: String,
	val latitude: String?,
	val longitude: String?,
	val telp: String?,
	val jenis_faskes: String?,
	val kelas_rs: String?,
	val status: String?,
	val detail: List<DetailFaskes>?,
	val source_data: String?,
) {
	fun toBookmarkedFaskes(): BookmarkedFaskes {
		return BookmarkedFaskes(
			0,
			kode,
			nama,
			telp,
			alamat,
			kota,
			provinsi,
			latitude,
			longitude,
			jenis_faskes,
			status
		)
	}
}
