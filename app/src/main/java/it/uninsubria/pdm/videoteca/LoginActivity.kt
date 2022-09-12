package it.uninsubria.pdm.videoteca

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import it.uninsubria.pdm.videoteca.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    //private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener{

            when{
                TextUtils.isEmpty(binding.etEmailLogin.text.toString().trim { it <= ' ' }) -> {
                    binding.etEmailLogin.setError(getString(R.string.insert_email_error))
                }

                TextUtils.isEmpty(binding.etPasswordLogin.text.toString().trim { it <= ' ' }) -> {
                    binding.etPasswordLogin.setError(getString(R.string.insert_password_error))
                }

                !this.pCheckEmail(binding.etEmailLogin.text.toString()) ->{
                    binding.etEmailLogin.setError(getString(R.string.valid_mail_error))
                }

                !this.pCheckPassword(binding.etPasswordLogin.text.toString()) -> {
                    binding.etPasswordLogin.setError(getString(R.string.valid_password_error))
                }

                else -> {

                    val email: String = binding.etEmailLogin.text.toString().trim { it <= ' ' }
                    val password: String = binding.etPasswordLogin.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(

                            OnCompleteListener<AuthResult> { task ->

                                if (task.isSuccessful) {

                                    var firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@LoginActivity,
                                        getString(R.string.login_success),
                                        Toast.LENGTH_SHORT
                                    ).show()


                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("userId", firebaseUser.uid)
                                    //intent.putExtra("email", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                }
            }


/*
            val userName = binding.etEmailLogin.text.toString()
            val psw = binding.etPasswordLogin.text.toString()
            val user_nodeName = userName

            database = FirebaseDatabase.getInstance().getReference("Users")
            database.child(user_nodeName).get().addOnSuccessListener{
                val pswFirebase = it.child("password").value.toString()
                if (psw == pswFirebase){
                    binding.etEmailLogin.text.clear()
                    binding.etPasswordLogin.text.clear()
                    Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
                    //chiamo la MainActivity passandole lo username come parametro
                } else {
                    Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show()
            }
*/


        }

    }


    private fun pCheckEmail(parEmail : String) : Boolean
    {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if(parEmail.matches(emailPattern.toRegex()))
            return true;
        else
            return false
    }

    private fun pCheckPassword(parPassword : String) : Boolean
    {
        val tPasswordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!\\-_?&])(?=\\S+$).{8,}"

        if(parPassword.matches(tPasswordPattern.toRegex()))
            return true
        else
            return false
    }




}