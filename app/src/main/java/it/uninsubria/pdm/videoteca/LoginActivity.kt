package it.uninsubria.pdm.videoteca

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import it.uninsubria.pdm.videoteca.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener{
            val userName = binding.etUserName.text.toString()
            val psw = binding.etPassword.text.toString()
            val user_nodeName = userName

            database = FirebaseDatabase.getInstance().getReference("Users")
            database.child(user_nodeName).get().addOnSuccessListener{
                val pswFirebase = it.child("password").value.toString()
                if (psw == pswFirebase){
                    binding.etUserName.text.clear()
                    binding.etPassword.text.clear()
                    Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
                    //chiamo la MainActivity passandole lo username come parametro
                } else {
                    Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show()
            }
        }

    }




}