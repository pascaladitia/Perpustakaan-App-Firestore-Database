package com.examplel.perpustakaanapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examplel.perpustakaanapp.databinding.ItemBukuUserBinding
import com.examplel.perpustakaanapp.model.Buku

class BukuUserAdapter (private val data: List<Buku>?,
                       private val itemClick: OnClickListener
) : RecyclerView.Adapter<BukuUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuUserAdapter.ViewHolder {
        val binding = ItemBukuUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemBukuUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Buku?) {
            binding.itemJudul.text = item?.judul
            binding.itemDesk.text = item?.desk
            binding.itemAuthor.text = item?.author

            binding.root.setOnClickListener {
                itemClick.detail(item)
            }
        }
    }

    interface OnClickListener {
        fun detail(item: Buku?)
    }
}