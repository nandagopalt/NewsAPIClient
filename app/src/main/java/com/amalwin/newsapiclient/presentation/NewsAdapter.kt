package com.amalwin.newsapiclient.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amalwin.newsapiclient.R
import com.amalwin.newsapiclient.data.model.Article
import com.amalwin.newsapiclient.databinding.NewsListItemBinding
import com.bumptech.glide.Glide

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.urlToImage == newItem.urlToImage
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differList = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val listItemBinding =
            NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(viewHolder: NewsViewHolder, position: Int) {
        viewHolder.bind(differList.currentList[position])
    }

    override fun getItemCount(): Int {
        return differList.currentList.size
    }

    inner class NewsViewHolder(private val newsListItemBinding: NewsListItemBinding) :
        RecyclerView.ViewHolder(newsListItemBinding.root) {
        fun bind(article: Article) {
            newsListItemBinding.apply {
                txtTitle.text = article.title
                txtDescription.text = article.description
                txtPublishingAt.text = article.publishedAt
                txtSource.text = article.source.name
                Glide.with(imageView.context).load(article.urlToImage)
                    .placeholder(R.drawable.ic_loading).into(imageView)
                root.setOnClickListener {
                    onClickListener?.let { itemOnClickListener ->
                        itemOnClickListener(article)
                    }
                }
            }

        }
    }

    private var onClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(clickListener: (Article) -> Unit) {
        onClickListener = clickListener
    }

}