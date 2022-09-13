package it.uninsubria.pdm.videoteca

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import it.uninsubria.pdm.videoteca.databinding.FilmNewBinding
import it.uninsubria.pdm.videoteca.ui.Film

class NewFilmActivity : AppCompatActivity() {

    private lateinit var binding: FilmNewBinding
    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilmNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etNewFilmTitle.text.clear()
        binding.etNewFilmDirector.text.clear()
        binding.etNewFilmYear.text.clear()
        binding.etNewFilmLength.text.clear()
        binding.etNewFilmDescription.text.clear()
        binding.etNewFilmAvailability.text.clear()
        binding.btnBack.text = getString(R.string.btn_back)
        binding.btnAddNewFilm.text = getString(R.string.btn_add)


        binding.btnBack.setOnClickListener {
            val intent =
                Intent(this, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.btnAddNewFilm.setOnClickListener{
            val title = binding.etNewFilmTitle.text.toString()
            val director = binding.etNewFilmDirector.text.toString()
            val year = binding.etNewFilmYear.text.toString()
            val length = binding.etNewFilmLength.text.toString()
            val description = binding.etNewFilmDescription.text.toString()
            val availability = binding.etNewFilmAvailability.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Films")
            val Film = Film(title, director, year, length, description, availability)
            val pNewFilmId = year + "_" + title
                    database.child(pNewFilmId).setValue(Film).addOnSuccessListener {
                binding.etNewFilmTitle.text.clear()
                binding.etNewFilmDirector.text.clear()
                binding.etNewFilmYear.text.clear()
                binding.etNewFilmLength.text.clear()
                binding.etNewFilmDescription.text.clear()
                binding.etNewFilmAvailability.text.clear()

                        val intent =
                            Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show()
            }
        }
    }


}





