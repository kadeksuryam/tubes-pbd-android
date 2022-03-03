package com.if3230.perludilindungi

import android.os.Bundle
import android.util.Base64
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.if3230.perludilindungi.databinding.ActivityNewsWebViewBinding

class NewsWebView : AppCompatActivity() {
	private lateinit var binding: ActivityNewsWebViewBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityNewsWebViewBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.newsWebViewTopBar.setNavigationOnClickListener {
			finish()
		}

		val b: Bundle? = intent.extras
		val url = b?.getString("url")
		val wv = binding.newsWebView

		wv.settings.javaScriptEnabled = true
		wv.webViewClient = WvClient(binding)

		if (url != null) {
			wv.loadUrl(url)
		} else {
			val notEncodedHtml = "<html><body><h1>Failed loading URL</h1></body></html"
			val encodedHtml = Base64.encodeToString(notEncodedHtml.toByteArray(), Base64.NO_PADDING)
			wv.loadData(encodedHtml, "text/html", "base64")
		}
	}

	private class WvClient(private val binding: ActivityNewsWebViewBinding) : WebViewClient() {
		override fun onPageFinished(view: WebView?, url: String?) {
			super.onPageFinished(view, url)
			binding.newsWebViewProgress.visibility = View.GONE
			binding.newsWebViewTopBar.title = view?.title
		}
	}
}