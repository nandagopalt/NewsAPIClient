package com.amalwin.newsapiclient.presentation

import androidx.recyclerview.widget.RecyclerView
import com.amalwin.newsapiclient.data.model.Article
import com.amalwin.newsapiclient.databinding.NewsListItemBinding

class NewsAdapter {
    inner class NewsViewHolder(newsListItemBinding: NewsListItemBinding) :
        RecyclerView.ViewHolder(newsListItemBinding.root) {
        fun bind(article: Article) {

        }
    }
}