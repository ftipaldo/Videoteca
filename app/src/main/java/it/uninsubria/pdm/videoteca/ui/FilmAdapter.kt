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
    private lateinit var mListener : onItemClickListener


    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.film_in_list,
                parent,
                false
            ),mListener
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

    fun updateDashboardFilmList(filmsList : List<Film>){
        this.filmsList.clear()
        val dim = filmsList.size
        this.filmsList.add(0, filmsList[dim-1])
        this.filmsList.add(1, filmsList[dim-2])
        //this.filmsList.addAll(filmsList)
        notifyDataSetChanged()
    }

    class FilmViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.tvFilmTitle)
        //val director: TextView = itemView.findViewById(R.id.   )
        val year: TextView = itemView.findViewById(R.id.tvFilmYear)
        val length: TextView = itemView.findViewById(R.id.tvFilmLength)
        //val description: TextView = itemView.findViewById(R.id.   )
        //val availability: TextView = itemView.findViewById(R.id.   )

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }


}