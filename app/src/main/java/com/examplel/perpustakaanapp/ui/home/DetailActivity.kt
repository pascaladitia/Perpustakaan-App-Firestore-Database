package com.examplel.perpustakaanapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.examplel.perpustakaanapp.databinding.ActivityDetailBinding
import com.examplel.perpustakaanapp.model.Perpustakaan

class DetailActivity : AppCompatActivity() {
    private var item: Perpustakaan? = null
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
    }

    private fun initView() {
        item = intent?.getParcelableExtra("data")

        if (item != null) {
            binding.detailJudul.setText(item?.judul)
            binding.detailIncomeTgl.setText(item?.tgl)
            binding.detailAuthor.setText(item?.peminjam)
            binding.detailDeskripsi.setText(item?.desk)
        }

        binding.btnDetailIncomeBack.setOnClickListener {
            onBackPressed()
        }
    }
}