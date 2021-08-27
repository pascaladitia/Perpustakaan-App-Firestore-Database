package com.examplel.perpustakaanapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examplel.perpustakaanapp.databinding.ItemPeminjamBinding
import com.examplel.perpustakaanapp.model.Perpustakaan

class PerpustakaanAdapter (private val data: List<Perpustakaan>?,
private val itemClick: OnClickListener
) : RecyclerView.Adapter<PerpustakaanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerpustakaanAdapter.ViewHolder {
        val binding = ItemPeminjamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemPeminjamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Perpustakaan?) {
            binding.itemJudul.text = item?.judul
            binding.itemPeminjam.text = item?.peminjam
            binding.itemTgl.text = item?.tgl

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
        fun update(item: Perpustakaan?)
        fun delete(item: Perpustakaan?)
        fun detail(item: Perpustakaan?)
    }
}