package it.uninsubria.pdm.videoteca.ui

data class Film (
    val title: String ?= null,
    val director: String ?= null,
    val year: String ?= null,
    val length: String ?= null,
    val description: String ?= null,
    val availability: String ?= null
    )