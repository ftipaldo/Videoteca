package it.uninsubria.pdm.videoteca.ui

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import java.lang.Exception

class FilmRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Films")

    @Volatile private var INSTANCE : FilmRepository ?= null

    fun getInstance() : FilmRepository{
        return INSTANCE ?: synchronized(this){
            val instance = FilmRepository()
            INSTANCE = instance
            instance
        }
    }


    fun loadFilms(filmList: MutableLiveData<List<Film>>){
        databaseReference.addValueEventListener(object : ValueEventListener{
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



}