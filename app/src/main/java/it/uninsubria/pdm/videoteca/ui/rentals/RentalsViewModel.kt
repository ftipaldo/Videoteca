package it.uninsubria.pdm.videoteca.ui.rentals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.uninsubria.pdm.videoteca.ui.Film
import it.uninsubria.pdm.videoteca.ui.FilmRepository

class RentalsViewModel : ViewModel() {

    private val repository : FilmRepository
    private val _allFilms = MutableLiveData<List<Film>>()
    val allFilms : LiveData<List<Film>> = _allFilms
    //val UID : String = "Z1b1ldRig4VNQ00j32v7AcVvjAB2"

    init {
        repository = FilmRepository().getInstance()
        repository.loadFilms(_allFilms)
    }

}