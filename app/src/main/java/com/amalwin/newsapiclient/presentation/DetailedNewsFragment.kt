package com.amalwin.newsapiclient.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.amalwin.newsapiclient.R
import com.amalwin.newsapiclient.databinding.FragmentDetailedNewsBinding


class DetailedNewsFragment : Fragment() {
    private lateinit var detailedNewsFragmentBinding: FragmentDetailedNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailedNewsFragmentBinding = FragmentDetailedNewsBinding.bind(view)
        val args: DetailedNewsFragmentArgs by navArgs()
        val selectedArticle = args.selectedArticle
        selectedArticle.let {
            detailedNewsFragmentBinding.webView.apply {
                webViewClient = WebViewClient()
                if (selectedArticle.url != "") {
                    detailedNewsFragmentBinding.webView.loadUrl(selectedArticle.url)
                }
            }
        }
    }

    companion object {
    }
}