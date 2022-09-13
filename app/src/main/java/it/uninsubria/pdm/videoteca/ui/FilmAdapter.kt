package it.uninsubria.pdm.videoteca.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uninsubria.pdm.videoteca.R


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
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    fun updateFilmList(filmsList : List<Film>){
        this.filmsList.clear()
        this.filmsList.addAll(filmsList)
        notifyDataSetChanged()
    }


  /* fun updateDashboardFilmList(filmsList : List<Film>){
        this.filmsList.clear()
        val dim = filmsList.size
        //if(dim<=5){
            this.filmsList.addAll(filmsList)
        /* } else {
            try {
                this.filmsList.add(0, filmsList[dim - 7])
                this.filmsList.add(1, filmsList[dim - 6])
                this.filmsList.add(2, filmsList[dim - 5])
                this.filmsList.add(3, filmsList[dim - 4])
                this.filmsList.add(4, filmsList[dim - 3])
                this.filmsList.add(5, filmsList[dim - 2])
                this.filmsList.add(6, filmsList[dim - 1])
            } catch (e: Exception){}
        } */
        notifyDataSetChanged()
    }   */

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