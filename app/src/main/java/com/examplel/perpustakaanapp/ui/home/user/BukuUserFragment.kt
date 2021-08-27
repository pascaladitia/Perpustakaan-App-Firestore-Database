package com.examplel.perpustakaanapp.ui.home.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.examplel.perpustakaanapp.R
import com.examplel.perpustakaanapp.adapter.BukuUserAdapter
import com.examplel.perpustakaanapp.databinding.FragmentBukuUserBinding
import com.examplel.perpustakaanapp.model.Buku
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class BukuUserFragment : Fragment() {

    private lateinit var navController: NavController
    private var db: FirebaseFirestore? = null
    private lateinit var binding: FragmentBukuUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBukuUserBinding.inflate(layoutInflater)
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
        binding.recyclerBuy.adapter = BukuUserAdapter(dataKas, object : BukuUserAdapter.OnClickListener {
            override fun detail(item: Buku?) {
                val bundle = bundleOf("data" to item)
                navController.navigate(R.id.action_bukuUserFragment_to_detailBukuActivity2, bundle)
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