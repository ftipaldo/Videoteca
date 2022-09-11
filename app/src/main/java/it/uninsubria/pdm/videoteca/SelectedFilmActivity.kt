package it.uninsubria.pdm.videoteca

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import it.uninsubria.pdm.videoteca.databinding.FilmSpecificBinding

class SelectedFilmActivity : AppCompatActivity() {

    private lateinit var binding: FilmSpecificBinding
    private lateinit var database: DatabaseReference

    private val film_nodeName = "1978_Grease"       //poi bisognerà riceverlo come parametro
    private val user_nodeName = "user001"           //poi bisognerà riceverlo come parametro


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilmSpecificBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("Films")
        database.child(film_nodeName).get().addOnSuccessListener{
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
            binding.tvSpecificFilmAvailability.text = "available: " + availability
            binding.btnSpecificFilmCommit.text = ">"

            binding.btnSpecificFilmCommit.setOnClickListener {
                rentFilm(film_nodeName, availability)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "404 Film Not Found", Toast.LENGTH_SHORT).show()
        }

    }



    private fun rentFilm(film_nodeName:String, availability:String){
        //lock sul db (?)
        //leggi valore availability
        //decrementi
        //scrivi nuovo valore sul db
        //aggiungi child in Users > id > Rentals con l'id del film (yyyy_fffff)
        //rimuovi il lock sul db

        val newAvailability = (availability.toInt() - 1).toString()
        val film_map = mapOf<String,String>(
            "availability" to newAvailability
        )
        database = FirebaseDatabase.getInstance().getReference("Films")
        database.child(film_nodeName).updateChildren(film_map).addOnSuccessListener{
            //Toast.makeText(this, "decremented", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            //Toast.makeText(this, "NOT decremented", Toast.LENGTH_SHORT).show()
        }

        val user_map = mapOf<String,String>(
            film_nodeName to "11/09/2022"       //poi data corrente
        )
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(user_nodeName).child("Rentals").updateChildren(user_map).addOnSuccessListener{
            //Toast.makeText(this, "Film rented", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            //Toast.makeText(this, "Film NOT rented", Toast.LENGTH_SHORT).show()
        }

    }






}




