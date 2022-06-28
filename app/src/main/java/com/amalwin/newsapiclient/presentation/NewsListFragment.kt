package com.amalwin.newsapiclient.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amalwin.newsapiclient.MainActivity
import com.amalwin.newsapiclient.R
import com.amalwin.newsapiclient.data.util.Resource
import com.amalwin.newsapiclient.databinding.FragmentNewsListBinding
import com.amalwin.newsapiclient.presentation.viewmodel.NewsViewModel

class NewsListFragment : Fragment() {
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsListBinding: FragmentNewsListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsListBinding = FragmentNewsListBinding.bind(view)
        newsViewModel = (activity as MainActivity).newsViewModel
        initRecyclerView()
        viewNewsList()
    }

    private fun initRecyclerView() {
        newsListBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        newsAdapter = NewsAdapter()
        newsListBinding.recyclerView.adapter = newsAdapter
    }

    private fun showProgressBar() {
        newsListBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        newsListBinding.progressBar.visibility = View.GONE
    }

    private fun viewNewsList() {
        newsViewModel.getTopHeadLines("us", 1)
        newsViewModel.newsHeadLines.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differList.submitList(it.articles.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "Error Occured : $it ", Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    companion object {

    }
}