package com.example.reddit.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.reddit.R
import com.example.reddit.data.model.ChildProperty
import com.example.reddit.databinding.ArticleItemBinding
import com.example.reddit.dialogues.TitleDialog

class RedditAdapter(var articles: List<ChildProperty>) :
    RecyclerView.Adapter<RedditAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = ArticleItemBinding.bind(view)
        var context = view.context

        fun bindArticle(article: ChildProperty) {
            /*article.data.num_comments = article.data.num_comments + " Comments"
            article.data.author = "Author: " + article.data.author*/
            binding.articleModel = article.data

            //Dialog to show the self text
            binding.tvTitle.setOnClickListener {
                val fragmentManager = (context as FragmentActivity).supportFragmentManager
                article.data.selftext?.let { selfText -> TitleDialog(selfText) }
                    ?.show(fragmentManager, TitleDialog::class.java.name)
            }

            binding.tvBrowser.setOnClickListener{
               val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.data.url))
                startActivity(context, browserIntent, null)
            }

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.article_item, viewGroup, false)
        )
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindArticle(articles[position])
    }
}