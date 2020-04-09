package com.douglas.githubauth.module.repositoryList

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.douglas.githubauth.R
import com.douglas.githubauth.databinding.ItemRepositoryBinding
import com.douglas.githubauth.domain.model.Repository

class RepositoryAdapter(
    var repositories: List<Repository> = emptyList()
): RecyclerView.Adapter<RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_repository,
            parent,
            false
        ) as ItemRepositoryBinding

        return RepositoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return repositories.count()
    }

    override fun onBindViewHolder(viewHolder: RepositoryViewHolder, position: Int) {
        val item = repositories[position]
        viewHolder.bind(item)
    }
}