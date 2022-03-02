package com.if3230.perludilindungi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.if3230.perludilindungi.databinding.FragmentNewsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [News.newInstance] factory method to
 * create an instance of this fragment.
 */
class News : Fragment() {
//	private lateinit var linearLayout: LinearLayout
	private lateinit var binding: FragmentNewsBinding
	private lateinit var viewModel: MainViewModel
	private var perluDilindungiAPI = PerduliLindungiAPI.getInstance()
	private val adapter = RecyclerViewNewsAdapter()

	private fun pxToDp(px: Int): Int {
		val dpScale = requireActivity().resources.displayMetrics.density
		return (px * dpScale + 0.5f).toInt()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(perluDilindungiAPI)))
			.get(MainViewModel::class.java)

		viewModel.news.observe(this) {
			adapter.newsList = it.results.toMutableList()
		}
		viewModel.errorMessage.observe(this) {
			Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		binding = FragmentNewsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.newsRecyclerView.setHasFixedSize(true)
		binding.newsRecyclerView.adapter = adapter

		viewModel.getNews()

//		linearLayout = view.findViewById(R.id.berita_main_layout)
//		val act = requireActivity()
//		linearLayout.gravity = Gravity.CENTER
//
//		for (_i in 0..30) {
//			val card = MaterialCardView(act)
//			val cardParams = LinearLayout.LayoutParams(
//				pxToDp(360),
//				LinearLayout.LayoutParams.WRAP_CONTENT
//			)
//			cardParams.setMargins(0, pxToDp(7), 0, pxToDp(7))
//			card.layoutParams = cardParams
//			card.setPadding(10, 0, 10, 0)
//			card.strokeWidth = 0
//			card.elevation = pxToDp(5).toFloat()
//
//			val containerAll = LinearLayout(act)
//			containerAll.layoutParams = ViewGroup.LayoutParams(
//				LinearLayout.LayoutParams.MATCH_PARENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT
//			)
//			containerAll.id = View.generateViewId()
//
//			val img = ImageView(act)
//			img.layoutParams = LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.MATCH_PARENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT,
//			)
//			img.id = View.generateViewId()
//
//			val innerLayout = LinearLayout(act)
//			innerLayout.layoutParams = LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.MATCH_PARENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT
//			)
//			innerLayout.id = View.generateViewId()
//			innerLayout.setPadding(pxToDp(16))
//
//			val title = TextView(act)
//			title.layoutParams = LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.MATCH_PARENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT
//			)
//			title.id = View.generateViewId()
//
//			val subtitle = TextView(act)
//			subtitle.layoutParams = LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.MATCH_PARENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT
//			)
//			subtitle.id = View.generateViewId()
//
//			img.setImageResource(R.drawable.ic_baseline_arrow_back_24)
//			img.contentDescription = act.getString(R.string.default_berita_content_description)
//
//			title.text = act.getString(R.string.title_berita_fragment)
//			title.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodyLarge)
//			title.setTextColor(act.getColor(R.color.md_theme_light_onSurface))
//
//			subtitle.text = act.getString(R.string.app_name)
//			subtitle.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodyMedium)
//			subtitle.setTextColor(act.getColor(R.color.md_theme_light_onSurfaceVariant))
//
//			innerLayout.orientation = LinearLayout.VERTICAL
//			innerLayout.addView(title)
//			innerLayout.addView(subtitle)
//
//			containerAll.orientation = LinearLayout.VERTICAL
//			containerAll.addView(img)
//			containerAll.addView(innerLayout)
//
//			card.addView(containerAll)
//
//			linearLayout.addView(card)
//		}
	}

	override fun onResume() {
		super.onResume()
		val act = requireActivity()
		val topBar: MaterialToolbar = act.findViewById(R.id.content_top_bar)!!
		topBar.title = act.getString(R.string.title_berita_fragment)
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @return A new instance of fragment Berita.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance() =
			News().apply {
				arguments = Bundle().apply { }
			}
	}
}