package com.examplel.perpustakaanapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.examplel.perpustakaanapp.databinding.ActivityRegisterBinding
import com.examplel.perpustakaanapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private lateinit var binding: ActivityRegisterBinding
    private var reference: DatabaseReference? = null
    private var db: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
    }

    private fun initView() {
        db = FirebaseDatabase.getInstance("https://mybook-71540-default-rtdb.firebaseio.com/")
        auth = FirebaseAuth.getInstance()
        reference = db!!.reference!!.child("user")


        binding.btnRegister.setOnClickListener {
            val email = binding.registerEmail.text.toString().trim()
            val password = binding.registerPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.registerEmail.error = "Email tidak boleh kosong"
            } else if (password.isEmpty()) {
                binding.registerPassword.error = "Password tidak boleh kosong"
            } else {
                actionRegister(email, password)
            }
        }
    }

    private fun actionRegister(email: String, password: String) {
        auth!!.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val userId = auth!!.currentUser!!.uid
                    //Verify Email
                    verifyEmail();
                    //update user profile information
                    val currentUserDb = reference!!.child(userId)

                    var status = "status"
                    if (binding.registerAdmin.isChecked) {
                        status = "admin"
                        val user = User(status, email, password)
                        currentUserDb.setValue(user)
                        intentAdmin()
                    } else {
                        status = "user"
                        val user = User(status, email, password)
                        currentUserDb.setValue(user)
                        intentAdmin()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(applicationContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun intentAdmin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = auth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Verification email sent to " + mUser.getEmail(), Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("TAG", "sendEmailVerification", task.exception)
                    Toast.makeText(applicationContext, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}