package it.uninsubria.pdm.videoteca

import android.app.Application

class GlobalVar : Application() {
    companion object{
        lateinit var glbUserId : String
        lateinit var isAdmin : String
    }


}