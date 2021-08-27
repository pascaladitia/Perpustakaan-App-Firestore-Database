package com.examplel.perpustakaanapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.examplel.perpustakaanapp.databinding.ActivityLoginBinding
import com.examplel.perpustakaanapp.ui.home.HomeActivity
import com.examplel.perpustakaanapp.ui.home.user.UserActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private var reference: DatabaseReference? = null
    private var db: FirebaseDatabase? = null

    private lateinit var binding: ActivityLoginBinding
    private val TAG = "Login Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
    }

    private fun initView() {
        db = FirebaseDatabase.getInstance("https://mybook-71540-default-rtdb.firebaseio.com/")
        auth = FirebaseAuth.getInstance()
        reference = db!!.reference!!.child("user")

        binding.btnLogin.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.loginEmail.error = "Email tidak boleh kosong"
            } else if (password.isEmpty()) {
                binding.loginPassword.error = "Password tidak boleh kosong"
            } else {
                actionLogin(email, password)
            }
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

//        if (auth?.currentUser?.email?.isNotEmpty() ?: false) {
//            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
//            finish()
//        }
    }

    private fun actionLogin(email: String, password: String) {
        Log.d(TAG, "Logging in user.")
        auth!!.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    updateUI()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(this@LoginActivity, "Authentication failed.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun updateUI() {
        val mUser = auth!!.currentUser
        val mUserReference = reference!!.child(mUser!!.uid)
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val status = snapshot.child("status").value.toString()

                if (binding.loginAdmin.isChecked) {
                    if (status.equals("admin")) {
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    } else {
                        showToast("Anda bukan admin")
                    }
                } else {
                    if (status.equals("user")) {
                        startActivity(Intent(this@LoginActivity, UserActivity::class.java))
                    } else {
                        showToast("Anda bukan user")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showToast(databaseError.message)
            }
        })
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}