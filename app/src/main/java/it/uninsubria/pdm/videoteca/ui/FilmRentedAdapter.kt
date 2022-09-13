package it.uninsubria.pdm.videoteca.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uninsubria.pdm.videoteca.R


class FilmRentedAdapter : RecyclerView.Adapter<FilmRentedAdapter.RentedFilmViewHolder>() {

    private val filmsList = ArrayList<Ren>()
    private lateinit var mListener : onItemClickListener


    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentedFilmViewHolder {
        return RentedFilmViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.film_in_rentals_list,
                parent,
                false
            ),mListener
        )
    }

    override fun onBindViewHolder(holder: RentedFilmViewHolder, position: Int) {

        val currentFilm = filmsList[position]

        //holder.ID.text = currentFilm.renFilmID
        holder.title.text = currentFilm.renFilmTitle
        holder.date.text = currentFilm.renFilmDate
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    fun updateFilmList(filmsList : List<Ren>){
        this.filmsList.clear()
        this.filmsList.addAll(filmsList)
        notifyDataSetChanged()
    }


    class RentedFilmViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        //val ID: TextView = itemView.findViewById(R.id.   )
        val title: TextView = itemView.findViewById(R.id.tvRenFilmTitle)
        //val director: TextView = itemView.findViewById(R.id.   )
        val date: TextView = itemView.findViewById(R.id.tvRenFilmDate)
        //val length: TextView = itemView.findViewById(R.id.   )
        //val description: TextView = itemView.findViewById(R.id.   )
        //val availability: TextView = itemView.findViewById(R.id.   )

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }


}