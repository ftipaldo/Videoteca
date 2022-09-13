package it.uninsubria.pdm.videoteca.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.uninsubria.pdm.videoteca.ui.Film
import it.uninsubria.pdm.videoteca.ui.FilmRepository


class HomeViewModel : ViewModel() {

    private val repository : FilmRepository
    private val _allFilms = MutableLiveData<List<Film>>()
    val allFilms : LiveData<List<Film>> = _allFilms

    init {
        repository = FilmRepository().getInstance()
        repository.loadFilms(_allFilms)
    }


    /* private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text */


}

