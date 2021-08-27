package com.examplel.perpustakaanapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examplel.perpustakaanapp.databinding.ItemBukuBinding
import com.examplel.perpustakaanapp.model.Buku

class BukuAdapter (private val data: List<Buku>?,
                   private val itemClick: OnClickListener
) : RecyclerView.Adapter<BukuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuAdapter.ViewHolder {
        val binding = ItemBukuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemBukuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Buku?) {
            binding.itemJudul.text = item?.judul
            binding.itemDesk.text = item?.desk
            binding.itemAuthor.text = item?.author

            binding.btnItemUpdate.setOnClickListener{
                itemClick.update(item)
            }

            binding.btnItemDelete.setOnClickListener {
                itemClick.delete(item)
            }

            binding.root.setOnClickListener {
                itemClick.detail(item)
            }
        }
    }

    interface OnClickListener {
        fun update(item: Buku?)
        fun delete(item: Buku?)
        fun detail(item: Buku?)
    }
}