package com.if3230.perludilindungi.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.if3230.perludilindungi.*
import com.if3230.perludilindungi.Model.BookmarkedFaskes
import com.if3230.perludilindungi.database.BookmarkedFaskesDatabase
import com.if3230.perludilindungi.databinding.FragmentFaskesDetailBinding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val FASKES_NAMA = "param1"
private const val FASKES_KODE = "param2"
private const val FASKES_JENIS = "param3"
private const val FASKES_ALAMAT = "param4"
private const val FASKES_KOTA = "param5"
private const val FASKES_PROVINSI = "param6"
private const val FASKES_TELP = "param7"
private const val FASKES_STATUS = "param8"
private const val FASKES_LAT = "param9"
private const val FASKES_LNG = "param10"

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
	private var faskesKota: String? = null
	private var faskesProvinsi: String? = null
	private var faskesTelp: String? = null
	private var faskesStatus: String? = null
	private var faskesLat: String? = null
	private var faskesLng: String? = null
	private var isBookmarked: Boolean = false
	private var id: Int? = null
	private lateinit var viewModel: MainViewModel
	private lateinit var binding: FragmentFaskesDetailBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			faskesNama = it.getString(FASKES_NAMA)
			faskesKode = it.getString(FASKES_KODE)
			faskesJenis = it.getString(FASKES_JENIS)
			faskesAlamat = it.getString(FASKES_ALAMAT)
			faskesKota = it.getString(FASKES_KOTA)
			faskesProvinsi = it.getString(FASKES_PROVINSI)
			faskesTelp = it.getString(FASKES_TELP)
			faskesStatus = it.getString(FASKES_STATUS)
			faskesLat = it.getString(FASKES_LAT)
			faskesLng = it.getString(FASKES_LNG)
		}
		val dao1 = BookmarkedFaskesDatabase
			.getInstance(requireContext())
			.bookmarkedFaskesDao()
		val api1 = PerluDilindungiAPI.getInstance()
		viewModel =
			ViewModelProvider(this, MainViewModelFactory(MainRepository(api1), dao1))
				.get(MainViewModel::class.java)
		viewModel.bookmarkByCode.observe(this) {
			isBookmarked = it != null

			val img = if (!isBookmarked) {
				// not bookmarked
				id = null
				binding.buttonBookmark.text = "Unbookmarked"
				ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_star_outline_24, null)
			} else {
				id = it!!._id
				binding.buttonBookmark.text = "Bookmarked"
				ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_star_24, null)
			}
			img!!.setBounds(0, 0, 60, 60)
			binding.buttonBookmark.setCompoundDrawables(img, null, null, null)
		}
		viewModel.getBookmarkByCode(faskesKode!!)
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

		binding.buttonBookmark.setOnClickListener {
			if (!isBookmarked) {
				viewModel.bookmark(
					BookmarkedFaskes(
						0,
						faskesKode!!,
						faskesNama!!,
						faskesTelp,
						faskesAlamat!!,
						faskesKota!!,
						faskesProvinsi!!,
						faskesLat,
						faskesLng,
						faskesJenis,
						faskesStatus,
					)
				)
			} else if (id != null) {
				viewModel.unbookmark(id!!)
			}
			viewModel.getBookmarkByCode(faskesKode!!)
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
			kota: String,
			provinsi: String,
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
					putString(FASKES_KOTA, kota)
					putString(FASKES_PROVINSI, provinsi)
					putString(FASKES_TELP, telp ?: "Tidak Diketahui")
					putString(FASKES_STATUS, status ?: "Tidak Diketahui")
					putString(FASKES_LAT, latitude ?: "0.0")
					putString(FASKES_LNG, longitude ?: "0.0")
				}
			}
	}
}