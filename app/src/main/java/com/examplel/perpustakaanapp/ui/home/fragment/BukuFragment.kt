package com.examplel.perpustakaanapp.ui.home.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.examplel.perpustakaanapp.R
import com.examplel.perpustakaanapp.adapter.BukuAdapter
import com.examplel.perpustakaanapp.databinding.FragmentBukuBinding
import com.examplel.perpustakaanapp.model.Buku
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class BukuFragment : Fragment() {

    private lateinit var navController: NavController
    private var db: FirebaseFirestore? = null
    private lateinit var binding: FragmentBukuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBukuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = FirebaseFirestore.getInstance()

        getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initView()
    }

    private fun initView() {
        binding.btnAdd.setOnClickListener {
            navController.navigate(R.id.action_bukuFragment_to_inputBukuActivity)
        }

        binding.recyclerBuy.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0 && !binding.btnAdd.isShown()) {
                    binding.btnAdd.show()
                }
                else if (dy > 0 && binding.btnAdd.isShown()) {
                    binding.btnAdd.hide()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                val que = db!!.collection("buku")
                que.whereEqualTo("judul", query)

                que.get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null) {
                            val kas = document.toObjects(Buku::class.java)
                            showData(kas)
                        } else {
                            println("No such document")
                        }
                    } else {
                        println(task.exception)
                    }
                }

                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                val que = db!!.collection("buku")
                que.whereEqualTo("judul", newText)

                que.get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null) {
                            val kas = document.toObjects(Buku::class.java)
                            showData(kas)
                        } else {
                            println("No such document")
                        }
                    } else {
                        println(task.exception)
                    }
                }
                return false
            }
        })
    }

    private fun getData() {
        db?.collection("buku")?.get()
            ?.addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null) {
                        val kas = document.toObjects(Buku::class.java)
                        showData(kas)
                    } else {
                        println("No such document")
                    }
                } else {
                    println(task.exception)
                }
            }

    }

    private fun showData(dataKas: MutableList<Buku>) {
        binding.recyclerBuy.adapter = BukuAdapter(dataKas, object : BukuAdapter.OnClickListener {
            override fun detail(item: Buku?) {
                val bundle = bundleOf("data" to item)
                navController.navigate(R.id.action_bukuFragment_to_detailBukuActivity, bundle)
            }

            override fun update(item: Buku?) {
                val bundle = bundleOf("data" to item)
                navController.navigate(R.id.action_bukuFragment_to_inputBukuActivity, bundle)
            }

            override fun delete(item: Buku?) {
                AlertDialog.Builder(context).apply {
                    setTitle("Hapus")
                    setMessage("Yakin ingin menghapus data?")
                    setCancelable(false)

                    setPositiveButton("ya") { dialogInterface, i ->

                        db?.collection("buku")
                            ?.document(item?.id.toString())?.delete()
                            ?.addOnSuccessListener {
                                showToast("Delete Success")
                            }

                        getData()
                        dialogInterface.dismiss()
                    }
                    setNegativeButton("Batal") {dialogInterface, i ->
                        dialogInterface?.dismiss()
                    }
                }.show()
            }

        })
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}