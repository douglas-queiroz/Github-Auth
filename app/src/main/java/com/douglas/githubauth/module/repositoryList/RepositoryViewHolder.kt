package com.douglas.githubauth.module.repositoryList

import android.support.v7.widget.RecyclerView
import com.douglas.githubauth.databinding.ItemRepositoryBinding
import com.douglas.githubauth.domain.model.Repository

class RepositoryViewHolder(
    val binding: ItemRepositoryBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(repository: Repository) {
        binding.titleTextView.text = repository.name
    }
}