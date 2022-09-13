package it.uninsubria.pdm.videoteca.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uninsubria.pdm.videoteca.GlobalVar
import it.uninsubria.pdm.videoteca.MainActivity
import it.uninsubria.pdm.videoteca.R
import it.uninsubria.pdm.videoteca.SelectedFilmActivity
import it.uninsubria.pdm.videoteca.databinding.FragmentHomeBinding
import it.uninsubria.pdm.videoteca.ui.FilmAdapter
import kotlinx.android.synthetic.main.activity_main.view.*


class HomeFragment : Fragment() {

    private lateinit var filmRecyclerView: RecyclerView
    private lateinit var adapter: FilmAdapter

    private var _binding: FragmentHomeBinding ?= null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        filmRecyclerView = binding.rvFilm
        filmRecyclerView.layoutManager = LinearLayoutManager(context)
        filmRecyclerView.setHasFixedSize(true)
        adapter = FilmAdapter()
        filmRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : FilmAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val film = homeViewModel.allFilms.value?.get(position)
                if (film != null) {
                    //Toast.makeText(activity, "element n. $position, film ${film.title}", Toast.LENGTH_SHORT).show()
                    val filmId = "${film.year}" + "_" + "${film.title}"
                    val intent =
                        Intent(activity, SelectedFilmActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("filmId", filmId)
                    startActivity(intent)

                }
            }
        })

        homeViewModel.allFilms.observe(viewLifecycleOwner, Observer{
            adapter.updateFilmList(it)
        })

        /* val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it } */

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filmRecyclerView = view.findViewById(R.id.rvFilm)
        filmRecyclerView.layoutManager = LinearLayoutManager(context)
        filmRecyclerView.setHasFixedSize(true)
        adapter = FilmAdapter()
        filmRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.allFilms.observe(viewLifecycleOwner, Observer{
            adapter.updateFilmList(it)
        })
    } */


}