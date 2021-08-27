package com.examplel.perpustakaanapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.examplel.perpustakaanapp.databinding.ActivityDetailBukuBinding
import com.examplel.perpustakaanapp.model.Buku

class DetailBukuActivity : AppCompatActivity() {
    private var item: Buku? = null
    private lateinit var binding: ActivityDetailBukuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
    }

    private fun initView() {
        item = intent?.getParcelableExtra("data")

        if (item != null) {
            binding.detailJudul.setText(item?.judul)
            binding.detailIncomeTgl.setText(item?.tgl)
            binding.detailAuthor.setText(item?.author)
            binding.detailDeskripsi.setText(item?.desk)
        }

        binding.btnDetailIncomeBack.setOnClickListener {
            onBackPressed()
        }
    }
}