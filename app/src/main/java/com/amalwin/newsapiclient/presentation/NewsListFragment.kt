package com.amalwin.newsapiclient.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amalwin.newsapiclient.MainActivity
import com.amalwin.newsapiclient.R
import com.amalwin.newsapiclient.data.util.Resource
import com.amalwin.newsapiclient.databinding.FragmentNewsListBinding
import com.amalwin.newsapiclient.presentation.viewmodel.NewsViewModel
import javax.inject.Inject

class NewsListFragment : Fragment() {

    private var isScrolling = false
    private var isLoading = false
    private var page = 1
    private var isLastPage = false
    private var pages = 0

    @Inject
    lateinit var newsAdapter: NewsAdapter

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
        newsAdapter = (activity as MainActivity).newsAdapter

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(
                R.id.action_newsListFragment_to_detailNewsFragment,
                bundle
            )
        }

        initRecyclerView()
        viewNewsList()
    }

    private fun initRecyclerView() {
        newsListBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        //newsAdapter = NewsAdapter()
        newsListBinding.recyclerView.adapter = newsAdapter
        newsListBinding.recyclerView.addOnScrollListener(scrollListener)

    }

    private fun showProgressBar() {
        isLoading = true
        newsListBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        newsListBinding.progressBar.visibility = View.GONE
    }

    private fun viewNewsList() {
        newsViewModel.getTopHeadLines("us", page)
        newsViewModel.newsHeadLines.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differList.submitList(it.articles.toList())
                        if (it.totalResults % 20 == 0) {
                            pages = it.totalResults / 20
                        } else {
                            pages = it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
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

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        /**
         * Callback method to be invoked when RecyclerView's scroll state changes.
         *
         *
         * @param recyclerView The RecyclerView whose scroll state has changed.
         * @param newState     The updated scroll state. One of [.SCROLL_STATE_IDLE],
         * [.SCROLL_STATE_DRAGGING] or [.SCROLL_STATE_SETTLING].
         */
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling = true
        }

        /**
         * Callback method to be invoked when the RecyclerView has been scrolled. This will be
         * called after the scroll has completed.
         *
         *
         * This callback will also be called if visible item range changes after a layout
         * calculation. In that case, dx and dy will be 0.
         *
         * @param recyclerView The RecyclerView which scrolled.
         * @param dx The amount of horizontal scroll.
         * @param dy The amount of vertical scroll.
         */
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = newsListBinding.recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val visibleItemCount = layoutManager.childCount
            val visibleTopItemPosition = layoutManager.findFirstVisibleItemPosition()
            val hasReachedToEnd = visibleItemCount + visibleTopItemPosition >= totalItemCount
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if (shouldPaginate) {
                page++
                newsViewModel.getTopHeadLines("us", page)
                isScrolling = false
            }
        }
    }

    companion object {

    }
}