package it.uninsubria.pdm.videoteca.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import it.uninsubria.pdm.videoteca.databinding.FragmentHomeBinding
import it.uninsubria.pdm.videoteca.ui.Film
import kotlinx.android.synthetic.*

class HomeFragment : Fragment() {

    /*
        private lateinit var dbref : DatabaseReference
        private lateinit var filmRecyclerView: RecyclerView
        private lateinit var filmArrayList: ArrayList<Film>
    */

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(

        /*
            //filmRecyclerView = findViewById(R.id.filmsList)
            //filmRecyclerView.layoutManager = LinearLayoutManager(this)
            //filmRecyclerView.setHasFixedSize(true)
            //filmArrayList = arrayListOf<Film>()
            //getFilmData()

        private getFilmData(){
            dbref = FirebaseDatabase.getInstance().getReference("Films")
        }
        */


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
}