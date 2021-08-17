package com.example.android.demoproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.demoproject.data.domain.Agriculture
import com.example.android.demoproject.databinding.ListItemAgricultureBinding

class AgricultureAdapter: ListAdapter<Agriculture, AgricultureAdapter.ViewHolder>(AgricultureDiffCallback()) {

    class ViewHolder private constructor(private val binding: ListItemAgricultureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Agriculture) {
            with(binding) {
                agricultureName.text = item.name
                averagePrice.text = item.avgPrice
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                return ViewHolder(ListItemAgricultureBinding.inflate(inflater, parent, false))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

private class AgricultureDiffCallback : DiffUtil.ItemCallback<Agriculture>() {
    override fun areItemsTheSame(oldItem: Agriculture, newItem: Agriculture): Boolean {
        // We don't have a id to distinguish items, compare the content for now
        return areContentsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Agriculture, newItem: Agriculture): Boolean {
        return oldItem == newItem
    }
}