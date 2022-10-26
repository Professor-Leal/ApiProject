package com.rafaelleal.android.apiproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafaelleal.android.apiproject.api.models.Article
import com.rafaelleal.android.apiproject.databinding.NewsTitleListItemBinding
import com.squareup.picasso.Picasso


class NewsAdapter(val listener: NewsListener) :
    ListAdapter<
            Article,
            NewsAdapter.ViewHolder
            >(ArticleDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent, listener)
    }

    /**
     * ViewHolder: Fixa os dados do modelo no item da lista
     */
    class ViewHolder private constructor(
        val binding: NewsTitleListItemBinding,
        val listener: NewsListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article, position: Int) {
            binding.apply {
                tvTitulo.text = item.title
                tvAutor.text = item.author
                tvDescricao.text = item.description
                tvFonte.text = item.source?.name
                Picasso.get()
                    .load(item.urlToImage)
                    .resize(100, 150)
                    .centerCrop()
                    .into(ivArticle)

                mainLayout.setOnClickListener {
                    listener.onItemClick(item)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: NewsListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsTitleListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}


class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.source?.id == newItem.source?.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}


interface NewsListener {
    fun onItemClick(Article: Article)
}
