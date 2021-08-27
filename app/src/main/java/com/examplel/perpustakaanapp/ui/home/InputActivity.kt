package com.examplel.perpustakaanapp.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.examplel.perpustakaanapp.databinding.ActivityInputBinding
import com.examplel.perpustakaanapp.model.Perpustakaan
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

class InputActivity : AppCompatActivity() {

    private var item: Perpustakaan? = null
    private var db: FirebaseFirestore? = null
    private lateinit var binding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        getParcel()
        initView()

    }

    private fun getParcel() {
        item = intent?.getParcelableExtra("data")

        db = FirebaseFirestore.getInstance()
    }

    private fun initView() {
        if (item != null) {
            binding.inputJudul.setText(item?.judul)
            binding.inputPeminjam.setText(item?.peminjam)
            binding.inputTgl.setText(item?.tgl)
            binding.inputDesk.setText(item?.desk)

            binding.btnIncomeSave.text = "Update"
        }

        inputData()
    }

    private fun inputData() {
        when (binding.btnIncomeSave.text) {
            "Update" -> {
                binding.btnIncomeSave.setOnClickListener {
                    var judul = binding.inputJudul.text.toString()
                    var peminjam = binding.inputPeminjam.text.toString()
                    var tgl = binding.inputTgl.text.toString()
                    var desk = binding.inputDesk.text.toString()

                    if (judul.isNotEmpty() && peminjam.isNotEmpty() && tgl.isNotEmpty()
                        && desk.isNotEmpty()
                    ) {

                        val data: HashMap<String, Any> = hashMapOf(
                            "judul" to judul,
                            "peminjam" to peminjam,
                            "tgl" to tgl,
                            "desk" to desk
                        )
                        db?.collection("perpus")?.document(item?.id.toString())
                            ?.update(data)?.addOnCompleteListener { task: Task<Void> ->
                                if (task.isSuccessful) {
                                    showToast("Update data berhasil")
                                    finish()
                                } else {
                                    println(task.exception)
                                }
                            }
                    } else {
                        showToast("Tidak boleh ada yang kosong")
                    }
                }
            }
            else -> {
                binding.btnIncomeSave.setOnClickListener {
                    var judul = binding.inputJudul.text.toString()
                    var peminjam = binding.inputPeminjam.text.toString()
                    var tgl = binding.inputTgl.text.toString()
                    var desk = binding.inputDesk.text.toString()

                    if (judul.isNotEmpty() && peminjam.isNotEmpty() && tgl.isNotEmpty()
                        && desk.isNotEmpty()
                    ) {

                        val ref = db?.collection("perpus")?.document()

                        val kas = Perpustakaan(ref?.id, judul, peminjam, tgl, desk)
                        db!!.collection("perpus").document(ref?.id.toString())
                            .set(kas).addOnSuccessListener {
                                showToast("Input data berhasil")
                                finish()
                            }.addOnFailureListener {
                                showToast("Input data gagal")
                            }
                    } else {
                        showToast("Tidak boleh ada yang kosong")
                    }
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}