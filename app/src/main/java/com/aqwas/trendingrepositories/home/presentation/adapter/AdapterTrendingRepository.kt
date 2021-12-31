package com.aqwas.trendingrepositories.home.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aqwas.trendingrepositories.core.presentation.extensions.layoutInflater
import com.aqwas.trendingrepositories.core.presentation.extensions.loadImage
import com.aqwas.trendingrepositories.databinding.RvRepositoryItemBinding
import com.aqwas.trendingrepositories.home.data.responseremote.ModelTrendingRepositoriesRemote

class AdapterTrendingRepository :
    ListAdapter<ModelTrendingRepositoriesRemote, RecyclerView.ViewHolder>(
        diffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            RvRepositoryItemBinding.inflate(parent.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        getItem(position)?.let {
            (holder as ViewHolder).bind(it)
        }
    }

    private inner class ViewHolder(private val binding: RvRepositoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelTrendingRepositoriesRemote) = with(binding) {
            tvAuthorName.text = item.author
            tvRepositoryName.text = item.name
            ivAuthorImg.loadImage(root.context, item.avatar)
            tvDescription.text = item.description
            tvLanguageName.text = item.language
            tvFork.text = item.forks
            tvStars.text = item.stars
            val gradientDrawable = GradientDrawable()
            gradientDrawable.setColor(Color.parseColor(item.languageColor))
            gradientDrawable.setSize(20, 20)
            ivColorLanguage.setImageDrawable(gradientDrawable)
            trendItemCardView.setOnClickListener {
                layoutSubDetails.isVisible = !layoutSubDetails.isVisible
            }
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ModelTrendingRepositoriesRemote>() {
            override fun areItemsTheSame(
                oldItem: ModelTrendingRepositoriesRemote,
                newItem: ModelTrendingRepositoriesRemote
            ): Boolean {
                return oldItem.name == newItem.name
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: ModelTrendingRepositoriesRemote,
                newItem: ModelTrendingRepositoriesRemote
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}