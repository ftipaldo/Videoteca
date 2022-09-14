package it.uninsubria.pdm.videoteca.ui.rentals

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uninsubria.pdm.videoteca.RentedFilmActivity
import it.uninsubria.pdm.videoteca.databinding.FragmentRentalsBinding
import it.uninsubria.pdm.videoteca.ui.FilmRentedAdapter

class RentalsFragment : Fragment() {

    private lateinit var filmRecyclerView: RecyclerView
    private lateinit var adapter: FilmRentedAdapter

    private var _binding: FragmentRentalsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rentalsViewModel =
            ViewModelProvider(this).get(RentalsViewModel::class.java)

        _binding = FragmentRentalsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        filmRecyclerView = binding.rvFilmRentals
        filmRecyclerView.layoutManager = LinearLayoutManager(context)
        filmRecyclerView.setHasFixedSize(true)
        adapter = FilmRentedAdapter()
        filmRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : FilmRentedAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val film = rentalsViewModel.allFilms.value?.get(position)
                if (film != null) {
                    //Toast.makeText(activity, "element n. $position, film ${film.renFilmID}", Toast.LENGTH_SHORT).show()
                val filmId = "${film.renFilmID}"
                val intent =
                    Intent(activity, RentedFilmActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("filmId", filmId)
                startActivity(intent)
                }
            }
        })

        rentalsViewModel.allFilms.observe(viewLifecycleOwner, Observer{
            adapter.updateFilmList(it)
        })



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}