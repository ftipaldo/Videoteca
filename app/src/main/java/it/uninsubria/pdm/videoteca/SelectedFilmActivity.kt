package it.uninsubria.pdm.videoteca

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import it.uninsubria.pdm.videoteca.databinding.FilmSpecificBinding
import it.uninsubria.pdm.videoteca.ui.Film

class SelectedFilmActivity : AppCompatActivity() {

    private lateinit var binding: FilmSpecificBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilmSpecificBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val childName = "1978_Grease"       //poi bisognerà riceverlo come parametro

        database = FirebaseDatabase.getInstance().getReference("Films")
        database.child(childName).get().addOnSuccessListener{
            val title = it.child("title").value
            val director = it.child("director").value
            val year = it.child("year").value
            val length = it.child("length").value
            val description = it.child("description").value
            val availability = it.child("availability").value
            binding.tvSpecificFilmTitle.text = title.toString()
            binding.tvSpecificFilmDirector.text = director.toString()
            binding.tvSpecificFilmYear.text = year.toString()
            binding.tvSpecificFilmLength.text = length.toString()
            binding.tvSpecificFilmDescription.text = description.toString()
            binding.tvSpecificFilmAvailability.text = "available: " + availability.toString()
            binding.btnSpecificFilmCommit.text = ">"

            //val filmSelected = Film(title.toString(), director.toString(), year.toString(), length.toString(), description.toString(), availability.toString())
            binding.btnSpecificFilmCommit.setOnClickListener {
                rentFilm(childName, title.toString(), director.toString(), year.toString(), length.toString(), description.toString(), availability.toString())
            }
        }.addOnFailureListener {
            Toast.makeText(this, "404 Film Not Found", Toast.LENGTH_SHORT).show()
        }



    }

    private fun rentFilm(childName:String, title:String, director:String, year:String, length:String, description:String, availability:String, ){
        //lock sul db (?)
        //leggi valore availability
        //decrementa
        //scrivi nuovo valore sul db
        //aggiungi child in Users > id > Rentals con l'id del film (yyyy_fffff)
        //rimuovo il lock sul db
        val newAvailability = (availability.toInt() - 1)
        database = FirebaseDatabase.getInstance().getReference("Films")
        val filmMap = mapOf<String,String>(
            "title" to title,
            "director" to director,
            "year" to year,
            "length" to length,
            "description" to description,
            "availability" to newAvailability.toString()
        )

        database.child(childName).updateChildren(filmMap).addOnSuccessListener{
            Toast.makeText(this, "Film rented", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Film NOT rented", Toast.LENGTH_SHORT).show()
        }

    }






}




