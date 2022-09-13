package it.uninsubria.pdm.videoteca

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import it.uninsubria.pdm.videoteca.databinding.FilmSpecificBinding
import java.text.SimpleDateFormat
import java.util.*

class RentedFilmActivity : AppCompatActivity() {

    private lateinit var binding: FilmSpecificBinding
    private lateinit var database: DatabaseReference

    private val pUserId = GlobalVar.glbUserId
    lateinit var pFilmId: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilmSpecificBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("filmId")) {
            pFilmId = intent.getStringExtra("filmId")!!
        }


        database = FirebaseDatabase.getInstance().getReference("Films")
        database.child(pFilmId).get().addOnSuccessListener{
            val title = it.child("title").value.toString()
            val director = it.child("director").value.toString()
            val year = it.child("year").value.toString()
            val length = it.child("length").value.toString()
            val description = it.child("description").value.toString()
            val availability = it.child("availability").value.toString()
            binding.tvSpecificFilmTitle.text = title
            binding.tvSpecificFilmDirector.text = director
            binding.tvSpecificFilmYear.text = year
            binding.tvSpecificFilmLength.text = length
            binding.tvSpecificFilmDescription.text = description
            binding.tvSpecificFilmAvailability.text = getString(R.string.available) + availability
            binding.btnSpecificFilmBack.text = getString(R.string.btn_back)
            binding.btnSpecificFilmCommit.text = getString(R.string.btn_commit_unrent)


            binding.btnSpecificFilmBack.setOnClickListener {
                val intent =
                    Intent(this, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }


            binding.btnSpecificFilmCommit.setOnClickListener {
                UNrentFilm(pUserId, pFilmId, availability)
                val intent =
                    Intent(this, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "404 Film Not Found", Toast.LENGTH_SHORT).show()
        }

    }



    private fun UNrentFilm(pUserId:String, pFilmId:String, availability:String){
        //lock sul db
        //incrementi availability
        //rimuovi child    con l'id del film (yyyy_fffff)    in Rentals > userId
        //rimuovi il lock sul db

        val newAvailability = (availability.toInt() + 1).toString()
        val film_map = mapOf<String,String>(
            "availability" to newAvailability
        )
        database = FirebaseDatabase.getInstance().getReference("Films")
        database.child(pFilmId).updateChildren(film_map).addOnSuccessListener{
            //Toast.makeText(this, "incremented", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            //Toast.makeText(this, "NOT incremented", Toast.LENGTH_SHORT).show()
        }

        database = FirebaseDatabase.getInstance().getReference("Rentals")
        database.child(pUserId).child(pFilmId).removeValue().addOnSuccessListener{
            //Toast.makeText(this, getString(R.string.unrent_successful), Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            //Toast.makeText(this, getString(R.string.unrent_successful), Toast.LENGTH_SHORT).show()
        }

    }




}