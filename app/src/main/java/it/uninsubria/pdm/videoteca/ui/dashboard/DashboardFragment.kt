package it.uninsubria.pdm.videoteca.ui.dashboard

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
import it.uninsubria.pdm.videoteca.databinding.FragmentDashboardBinding
import it.uninsubria.pdm.videoteca.ui.FilmAdapter

class DashboardFragment : Fragment() {

    private lateinit var filmRecyclerView: RecyclerView
    private lateinit var adapter: FilmAdapter

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root



        filmRecyclerView = binding.rvFilmDashboard
        filmRecyclerView.layoutManager = LinearLayoutManager(context)
        filmRecyclerView.setHasFixedSize(true)
        adapter = FilmAdapter()
        filmRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : FilmAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(activity, "element n. $position", Toast.LENGTH_SHORT).show()
            }
        })

        dashboardViewModel.allFilms.observe(viewLifecycleOwner, Observer{
            adapter.updateDashboardFilmList(it)
        })



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}