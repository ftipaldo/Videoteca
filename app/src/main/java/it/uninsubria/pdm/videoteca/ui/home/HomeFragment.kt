package it.uninsubria.pdm.videoteca.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uninsubria.pdm.videoteca.R
import it.uninsubria.pdm.videoteca.databinding.FragmentHomeBinding
import it.uninsubria.pdm.videoteca.ui.FilmAdapter


class HomeFragment : Fragment() {

        private lateinit var viewModel: HomeViewModel
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

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
    }


}