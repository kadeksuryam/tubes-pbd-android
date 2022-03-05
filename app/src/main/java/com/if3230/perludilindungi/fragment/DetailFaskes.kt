package com.if3230.perludilindungi.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.if3230.perludilindungi.databinding.FragmentFaskesDetailBinding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val FASKES_NAMA = "param1"
private const val FASKES_KODE = "param2"
private const val FASKES_JENIS = "param3"
private const val FASKES_ALAMAT = "param4"
private const val FASKES_TELP = "param5"
private const val FASKES_STATUS = "param6"
private const val FASKES_LAT = "param7"
private const val FASKES_LNG = "param8"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFaskes.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFaskes : Fragment() {
	private var faskesNama: String? = null
	private var faskesKode: String? = null
	private var faskesJenis: String? = null
	private var faskesAlamat: String? = null
	private var faskesTelp: String? = null
	private var faskesStatus: String? = null
	private var faskesLat: String? = null
	private var faskesLng: String? = null
	private lateinit var binding: FragmentFaskesDetailBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			faskesNama = it.getString(FASKES_NAMA)
			faskesKode = it.getString(FASKES_KODE)
			faskesJenis = it.getString(FASKES_JENIS)
			faskesAlamat = it.getString(FASKES_ALAMAT)
			faskesTelp = it.getString(FASKES_TELP)
			faskesStatus = it.getString(FASKES_STATUS)
			faskesLat = it.getString(FASKES_LAT)
			faskesLng = it.getString(FASKES_LNG)
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		binding = FragmentFaskesDetailBinding.inflate(inflater, container, false)
		binding.namaFaskes.text = faskesNama
		binding.codeFaskes.text = faskesKode
		binding.tipeFaskes.text = faskesJenis
		binding.alamatFaskes.text = faskesAlamat
		binding.noFaskes.text = faskesTelp
		binding.vaksinFaskes.text = faskesStatus

		binding.buttonGmaps.setOnClickListener {
			val gmmIntentUri = Uri.parse("geo:$faskesLat,$faskesLng")
			val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
			mapIntent.setPackage("com.google.android.apps.maps")
			startActivity(mapIntent)
		}

		return binding.root
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param nama Parameter 1.
		 * @param kode Parameter 2.
		 * @param jenis Parameter 2.
		 * @param alamat Parameter 2.
		 * @param telp Parameter 2.
		 * @param status Parameter 2.
		 * @param latitude Parameter 2.
		 * @param longitude Parameter 2.
		 * @return A new instance of fragment DetailFaskes.
		 */
		@JvmStatic
		fun newInstance(
			nama: String,
			kode: String,
			jenis: String?,
			alamat: String,
			telp: String?,
			status: String?,
			latitude: String?,
			longitude: String?,
		) =
			DetailFaskes().apply {
				arguments = Bundle().apply {
					putString(FASKES_NAMA, nama)
					putString(FASKES_KODE, kode)
					putString(FASKES_JENIS, jenis ?: "-")
					putString(FASKES_ALAMAT, alamat)
					putString(FASKES_TELP, telp ?: "Tidak Diketahui")
					putString(FASKES_STATUS, status ?: "Tidak Diketahui")
					putString(FASKES_LAT, latitude ?: "0.0")
					putString(FASKES_LNG, longitude ?: "0.0")
				}
			}
	}
}