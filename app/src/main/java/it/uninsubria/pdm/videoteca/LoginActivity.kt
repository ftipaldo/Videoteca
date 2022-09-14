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

                                    //imposto lo userID globale
                                    GlobalVar.glbUserId = firebaseUser.uid

                                    val databaseReferenceAdmin : DatabaseReference = FirebaseDatabase.getInstance().getReference("Admin")
                                    databaseReferenceAdmin.child(firebaseUser.uid).get().addOnSuccessListener {
                                        val adm = it.value.toString()
                                        //imposto isAdmin globale
                                        if (adm == "admin") {
                                            GlobalVar.isAdmin = "true"
                                        } else {
                                            GlobalVar.isAdmin = "false"
                                        }
                                    }


                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    //intent.putExtra("userId", firebaseUser.uid)
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