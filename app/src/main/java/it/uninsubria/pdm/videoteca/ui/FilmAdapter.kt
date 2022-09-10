package it.uninsubria.pdm.videoteca.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uninsubria.pdm.videoteca.R
import kotlinx.android.synthetic.main.film_in_list.view.*

class FilmAdapter : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    private val filmsList = ArrayList<Film>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.film_in_list,
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {

        val currentFilm = filmsList[position]

        holder.title.text = currentFilm.title
        holder.year.text = currentFilm.year
        holder.length.text = currentFilm.length

        /* holder.itemView.apply {
            tvFilmTitle.text = currentFilm.title
            tvFilmYear.text = currentFilm.year
            tvFilmLength.text = currentFilm.length
        } */
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    fun updateFilmList(filmsList : List<Film>){
        this.filmsList.clear()
        this.filmsList.addAll(filmsList)
        notifyDataSetChanged()
    }

    class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.tvFilmTitle)
        //val director: TextView = itemView.findViewById(R.id.   )
        val year: TextView = itemView.findViewById(R.id.tvFilmYear)
        val length: TextView = itemView.findViewById(R.id.tvFilmLength)
        //val description: TextView = itemView.findViewById(R.id.   )
        //val availability: TextView = itemView.findViewById(R.id.   )
    }


}