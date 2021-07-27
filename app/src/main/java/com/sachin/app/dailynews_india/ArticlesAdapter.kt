package com.sachin.app.dailynews_india

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sachin.app.dailynews_india.model.Article
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.article_layout.view.*

class ArticlesAdapter :
    ListAdapter<Article, ArticlesAdapter.NewsViewHolder>(Article.DIFF_CALLBACK) {

    private var onItemClick: (Article) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.article_layout, parent, false)
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) =
        holder.bindTo(getItem(position))

    inner class NewsViewHolder(override val containerView: View) : LayoutContainer,
        RecyclerView.ViewHolder(containerView) {

        fun bindTo(article: Article) {
            with(containerView) {
                Glide.with(context).load(article.urlToImage).centerCrop().thumbnail(0.1F)
                    .into(news_image)
                news_title.text = article.title
                news_desc.text = article.description
                news_source.text = article.source.name
                news_published_at.text = article.publishedAt
                setOnClickListener { onItemClick.invoke(article) }
            }
        }
    }

    fun setOnItemClickListener(onItemClick: (Article) -> Unit) {
        this.onItemClick = onItemClick
    }
}