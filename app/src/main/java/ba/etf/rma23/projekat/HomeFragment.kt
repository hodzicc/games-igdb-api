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
    private val lista = getAll()
    private lateinit var gameListAdapter: GamesListAdapter
    private lateinit var searchEditText: EditText
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*
        if (container != null) {
            container.removeAllViews();
        }


         */

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_button = view.findViewById(R.id.search_button)
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
        gameListAdapter.updateGames(lista)
        search_button.isClickable = true
        search_button.setOnClickListener {

            val searchTerm = searchEditText.text.toString()
            val gameRepository = GamesRepository
            val scope = CoroutineScope( Dispatchers.Main)
            scope.launch {
                try {
                    val games = gameRepository.getGamesByName(searchTerm)
                    gameListAdapter.updateGames(games)

                    AccountGamesRepository.setHash("5a1208ce-21bf-4b1f-917b-ffd95937298f")
                    AccountGamesRepository.saveGame(Game(24273,"Age of Empires: The Age of Kings","","",10.0,"","","","","","",listOf<UserImpression>()))
                   // AccountGamesRepository.saveGame(Game(47076,"Age of Empires: Gold Edition","","",10.0,"","","","","","",listOf<UserImpression>()))
            //      println("bllabla")
                  println("prije"+AccountGamesRepository.getSavedGames().size)
                    AccountGamesRepository.removeGame(24273)
                    println("poslije"+AccountGamesRepository.getSavedGames().size)
                    //println(AccountGamesRepository.getHash())
                } catch (e: Exception) {
                    // Handle any errors that occurred during the search
                    // Display an error message or perform appropriate error handling
                }
            }

        }



    }


    private fun showGameDetails(game: Game) {

        sharedViewModel.isGameDetailsOpened.value=true
        sharedViewModel.gametitle.value=game.title

    }


}