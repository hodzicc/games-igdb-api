package com.example.spirala1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala1.GameData.Companion.GetDetails
import com.example.spirala1.GameData.Companion.getAll

// TODO: Rename parameter arguments, choose names that match
/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var game1: Game
    private lateinit var search_button: Button
    private lateinit var game_list: RecyclerView
    private val lista = getAll()
    private lateinit var gameListAdapter: GamesListAdapter
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            container.removeAllViews();
        }
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_button = view.findViewById(R.id.search_button)
        game_list = view.findViewById(R.id.game_list)
        game_list.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false )

        gameListAdapter = GamesListAdapter(arrayListOf()) { game ->
            showGameDetails(game)
        }

        game_list.adapter = gameListAdapter
        gameListAdapter.updateGames(lista)

    }

    private fun showGameDetails(game: Game) {

        sharedViewModel.gametitle.value=game.title

    }


}