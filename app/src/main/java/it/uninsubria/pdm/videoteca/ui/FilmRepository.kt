package it.uninsubria.pdm.videoteca.ui

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class FilmRepository {
    private val databaseReferenceFilms : DatabaseReference = FirebaseDatabase.getInstance().getReference("Films")
    private val databaseReferenceRentals : DatabaseReference = FirebaseDatabase.getInstance().getReference("Rentals")

    @Volatile private var INSTANCE : FilmRepository ?= null

    fun getInstance() : FilmRepository{
        return INSTANCE ?: synchronized(this){
            val instance = FilmRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadFilms(filmList: MutableLiveData<List<Film>>){
        databaseReferenceFilms.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _filmList : List<Film> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Film::class.java)!!
                    }
                    filmList.postValue(_filmList)
                } catch (e : Exception){
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    fun loadRentals(rentalsList: MutableLiveData<List<Ren>>, pUserId : String){
        databaseReferenceRentals.child(pUserId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                        val RFL : MutableList<Ren> = ArrayList()
                        //loop sui nodi figli di pUserId
                        for (snap: DataSnapshot in snapshot.children) {
                            //recupero le info del nodo visitato
                            val kEy = snap.key.toString()
                            val vAl = snap.value.toString()  //snap.value potrebbe essere null, devo gestirlo ?
                            //recupero il titolo del film dal DB
                            var infoFilm = databaseReferenceFilms.child(kEy).get()
                            while (! infoFilm.isComplete){}
                            val renObj = Ren (
                                renFilmID = kEy,
                                renFilmTitle = infoFilm.result.child("title").value.toString(),
                                renFilmDate = vAl )
                            //aggiungo l'oggetto alla lista RFL da restituire
                            RFL.add(renObj)
                        }
                    rentalsList.postValue(RFL)
                } catch (e : Exception){
                    val x = e.message
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }



}