package com.fmollea.platzichallenge.ui.movieDetail.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fmollea.platzichallenge.BuildConfig
import com.fmollea.platzichallenge.core.BaseViewHolder
import com.fmollea.platzichallenge.data.model.movieCast.MovieCast
import com.fmollea.platzichallenge.databinding.MovieCastItemRowBinding

class MovieCastAdapter(private val movieCastList: List<MovieCast>)
    : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding = MovieCastItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  MovieCastViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (holder is MovieCastViewHolder) {
            holder.bind(movieCastList[position])
        }
    }

    override fun getItemCount() = movieCastList.size

    private inner class MovieCastViewHolder(val binding: MovieCastItemRowBinding, val context: Context) :
        BaseViewHolder<MovieCast>(binding.root) {

        override fun bind(item: MovieCast) {
            binding.tvName.text = item.name
            binding.tvCharacter.text = item.character
            Glide.with(context)
                .load("${BuildConfig.IMAGE_URL}${item.profilePath}")
                .centerCrop()
                .into(binding.imgProfile)
        }
    }
}