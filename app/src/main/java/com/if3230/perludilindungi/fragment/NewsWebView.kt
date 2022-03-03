package com.if3230.perludilindungi.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.if3230.perludilindungi.R
import com.if3230.perludilindungi.databinding.FragmentNewsDetailBinding

// the fragment initialization parameters
private const val ARG_URL =
	"https://covid19.go.id/artikel/2022/02/11/angka-kesembuhan-covid-19-terus-meningkat-hingga-4250277-orang"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsWebView.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsWebView : Fragment() {
	private var url: String? = null
	private lateinit var binding: FragmentNewsDetailBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			url = it.getString(ARG_URL)
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		binding = FragmentNewsDetailBinding.inflate(layoutInflater, container, false)

		val wv = binding.newsWebView
		wv.settings.javaScriptEnabled = true
		wv.webViewClient = NewsWebView.WvClient(binding)

		if (url != null) {
			wv.loadUrl(url!!)
		} else {
			val notEncodedHtml = "<html><body><h1>Failed loading URL</h1></body></html"
			val encodedHtml = Base64.encodeToString(notEncodedHtml.toByteArray(), Base64.NO_PADDING)
			wv.loadData(encodedHtml, "text/html", "base64")
		}

		return binding.root
	}

	override fun onResume() {
		super.onResume()
		val act = requireActivity()
		val topBar: MaterialToolbar = act.findViewById(R.id.content_top_bar)!!
		topBar.title = act.getString(R.string.title_news_fragment)
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param url URL for webview
		 * @return A new instance of fragment NewsDetail.
		 */
		@JvmStatic
		fun newInstance(url: String) =
			NewsWebView().apply {
				arguments = Bundle().apply {
					putString(ARG_URL, url)
				}
			}
	}

	private class WvClient(private val binding: FragmentNewsDetailBinding) : WebViewClient() {
		override fun onPageFinished(view: WebView?, url: String?) {
			super.onPageFinished(view, url)
			binding.newsWebViewProgress.visibility = View.GONE
		}
	}
}