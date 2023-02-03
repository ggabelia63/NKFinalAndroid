package com.example.countriesapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.countriesapp.R

class MapFragment : BaseFragment() {

    private lateinit var mapWebView: WebView

    override fun getLayout() = R.layout.map_fragment

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapWebView = requireView().findViewById(R.id.mapWebView)

        val googleMapLink = arguments?.getString(GOOGLE_MAP_LINK) ?: ""

        mapWebView.settings.javaScriptEnabled = true
        mapWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        mapWebView.settings.allowFileAccess = true
        mapWebView.settings.domStorageEnabled = true
        mapWebView.webViewClient = WebViewClient()
        mapWebView.loadUrl(googleMapLink)
    }

    companion object {
        const val GOOGLE_MAP_LINK = "Google map link"
    }

}