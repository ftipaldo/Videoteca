package it.uninsubria.pdm.videoteca.ui.rentals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.uninsubria.pdm.videoteca.GlobalVar
import it.uninsubria.pdm.videoteca.ui.Film
import it.uninsubria.pdm.videoteca.ui.FilmRepository
import it.uninsubria.pdm.videoteca.ui.Ren

class RentalsViewModel : ViewModel() {

    private val repository : FilmRepository
    private val _allFilms = MutableLiveData<List<Ren>>()
    val allFilms : LiveData<List<Ren>> = _allFilms

    private val pUserId = GlobalVar.glbUserId

    init {
        repository = FilmRepository().getInstance()
        repository.loadRentals(_allFilms, pUserId)
    }

}