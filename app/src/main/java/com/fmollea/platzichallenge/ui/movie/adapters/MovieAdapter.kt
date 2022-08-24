package com.fmollea.platzichallenge.ui.movie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.fmollea.platzichallenge.BuildConfig
import com.fmollea.platzichallenge.core.BaseViewHolder
import com.fmollea.platzichallenge.data.model.movie.Movie
import com.fmollea.platzichallenge.databinding.MovieItemRowBinding

class MovieAdapter(
    private val itemClickListener: OnMovieClickListener
) : PagingDataAdapter<Movie, BaseViewHolder<*>>( diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding = MovieItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MovieViewHolder(binding, parent.context)

        binding.root.setOnClickListener {

            val position = holder.bindingAdapterPosition.takeIf { position ->
                position != DiffUtil.DiffResult.NO_POSITION
            } ?: return@setOnClickListener

            getItem(position)?.let { item ->
                itemClickListener.onMovieClick(item)
            }

        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        getItem(position)?.let { item ->
            if (holder is MovieViewHolder) {
                holder.bind(item)
            }
        }
    }

    private inner class MovieViewHolder(val binding: MovieItemRowBinding, val context: Context) :
        BaseViewHolder<Movie>(binding.root) {

        override fun bind(item: Movie) {
            binding.tvTitle.text = item.title
            binding.tvOverview.text = item.overview
            Glide.with(context)
                .load("${BuildConfig.IMAGE_URL}${item.posterPath}")
                .centerCrop()
                .into(binding.imgMovie)
        }
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        }
    }
}