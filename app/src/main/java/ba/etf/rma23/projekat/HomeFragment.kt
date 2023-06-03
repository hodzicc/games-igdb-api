package ba.etf.rma23.projekat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.GameData.Companion.getAll
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.rma23.projekat.data.repositories.IGDBApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


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
    private lateinit var lista: List<Game>
    private lateinit var gameListAdapter: GamesListAdapter
    private lateinit var searchEditText: EditText
    private lateinit var sort_button: Button
    private lateinit var favorites_button: Button
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AccountGamesRepository.setHash("5a1208ce-21bf-4b1f-917b-ffd95937298f")
        search_button = view.findViewById(R.id.search_button)
        sort_button = view.findViewById(R.id.sort_button)
        favorites_button = view.findViewById(R.id.favorites_button)
        game_list = view.findViewById(R.id.game_list)
        searchEditText = view.findViewById(R.id.search_query_edittext)
        game_list.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false )

        gameListAdapter = GamesListAdapter(arrayListOf()) { game ->
            showGameDetails(game)
        }

        game_list.adapter = gameListAdapter
        val scope = CoroutineScope( Dispatchers.Main)
        scope.launch {
            try {

                val games = AccountGamesRepository.getSavedGames()
                gameListAdapter.updateGames(games)
                search_button.isClickable = true
                search_button.setOnClickListener {

                    val searchTerm = searchEditText.text.toString()
                    val gameRepository = GamesRepository
                    val scope = CoroutineScope( Dispatchers.Main)
                    scope.launch {
                        try {
                            val games = gameRepository.getGamesByName(searchTerm)
                            gameListAdapter.updateGames(games)

                        } catch (e: Exception) {
                            // Handle any errors that occurred during the search
                            // Display an error message or perform appropriate error handling
                        }
                    }

                }

                sort_button.isClickable = true
                sort_button.setOnClickListener {

                    val gameRepository = GamesRepository
                    val scope = CoroutineScope( Dispatchers.Main)
                    scope.launch {
                        try {
                            val games = gameRepository.sortGames()
                            gameListAdapter.updateGames(games)

                        } catch (e: Exception) {
                            // Handle any errors that occurred during the search
                            // Display an error message or perform appropriate error handling
                        }
                    }

                }

                favorites_button.isClickable = true
                favorites_button.setOnClickListener {

                    val gameRepository = GamesRepository
                    val scope = CoroutineScope( Dispatchers.Main)
                    scope.launch {
                        try {
                            val games = AccountGamesRepository.getSavedGames()
                            gameListAdapter.updateGames(games)

                        } catch (e: Exception) {
                            // Handle any errors that occurred during the search
                            // Display an error message or perform appropriate error handling
                        }
                    }

                }


            } catch (e: Exception) {
            }
        }





    }


    private fun showGameDetails(game: Game) {

        sharedViewModel.isGameDetailsOpened.value=true
        sharedViewModel.gametitle.value=game.title
        sharedViewModel.gameid.value=game.id

    }


}