package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameDetailsFragment : Fragment() {
    private lateinit var game: Game
    private lateinit var title: TextView
    private lateinit var img: ImageView
    private lateinit var platform: TextView
    private lateinit var release: TextView
    private lateinit var esrb: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView
    private lateinit var addButton: Button
    private lateinit var removeButton: Button
    private lateinit var impressionsView: RecyclerView
    private lateinit var impressionsAdapter: ImpressionsListAdapter
    private var impressions: List<UserImpression> = emptyList()
    private lateinit var addImpression: Button
    private lateinit var reviewEditText: EditText
    private lateinit var ratingBarImpression: RatingBar
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_game_details, container, false)

        return view
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.item_title_textview)
        img = view.findViewById(R.id.cover_imageview)
        platform = view.findViewById(R.id.platform_textview)
        release = view.findViewById(R.id.release_date_textview)
        esrb = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)
        description = view.findViewById(R.id.description_textview)
        addButton = view.findViewById(R.id.add_button)
        removeButton = view.findViewById(R.id.remove_button)
        addImpression = view.findViewById(R.id.send_impressions)
        reviewEditText = view.findViewById(R.id.review_edittext)
        ratingBarImpression = view.findViewById(R.id.rating_bar_impression)

        val args = arguments
        var message = args?.getString("message")
        if (message != null) {
        //    sharedViewModel.isGameDetailsOpened.value=true
        }
        else message = "Fortnite"

         //   game = getGameByTitle(message)
        var favGames:List<Game> =listOf()
        val scope = CoroutineScope( Dispatchers.Main)
        scope.launch {
            try {
                var listica = GamesRepository.getGamesByName(message)
                favGames = AccountGamesRepository.getSavedGames()
                for (i in listica) {
                    if (i.id == sharedViewModel.gameid.value)
                        game = i
                }
            //    populateDetails()

            //    var userImpressionsList = game.userImpressions.sortedByDescending { it.timestamp }
                impressionsView = view.findViewById(R.id.reviews_list)
                val layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                 impressionsView.layoutManager = layoutManager
                impressionsAdapter = ImpressionsListAdapter(listOf())
                impressionsView.adapter = impressionsAdapter

                for (i in favGames.indices) {
                    if (favGames[i].id == game.id) {
                        addButton.isEnabled = false
                    }
                }

                var impressionsFromServer = GameReviewsRepository.getReviewsForGame(game.id)
                impressionsFromServer.forEach { gameReview ->
                    addReview(gameReview)
                }
                impressions.sortedByDescending { it.timestamp }
                impressionsAdapter.updateUserImpressions(impressions)


                var rating: Int? = null;
                ratingBarImpression.setOnRatingBarChangeListener { _, newRating, _ ->
                    rating = newRating.toInt();
                }

                addImpression.setOnClickListener {
                    val scope = CoroutineScope(Job() + Dispatchers.Main)
                    scope.launch {
                        var reviewText: String? = reviewEditText.text.toString()
                        if (reviewText == "")
                            reviewText = null

                        var review = GameReview(
                            rating,
                            reviewText,
                            game.id,
                            true,
                            "",
                            System.currentTimeMillis().toString()
                        )
                        GameReviewsRepository.sendReview(
                            requireContext(), review
                        )


                        addReview(review)
                        impressionsAdapter.updateUserImpressions(impressions)
                        populateDetails()
                    }
                }
                populateDetails()

                    addButton.setOnClickListener {
                        val scope = CoroutineScope(Dispatchers.IO)
                        scope.launch {
                            AccountGamesRepository.saveGame(game)

                        }
                        addButton.isEnabled = false
                        removeButton.isEnabled = true
                    }

                    removeButton.setOnClickListener {
                        val scope = CoroutineScope(Dispatchers.IO)
                        scope.launch {
                            AccountGamesRepository.removeGame(game.id)
                        }
                        addButton.isEnabled = true
                        removeButton.isEnabled = false
                    }

            }catch (e: Exception) {
                // Handle any errors that occurred during the search
                // Display an error message or perform appropriate error handling
            }
        }




    }

    private fun addReview(gameReview: GameReview) {
        if (gameReview.review != null && gameReview.review != "")
            impressions += UserReview(
                gameReview.student!!,
                gameReview.timestamp!!.toLong(), gameReview.review!!
            )
        if (gameReview.rating != null)
            impressions += UserRating(
                gameReview.student!!,
                gameReview.timestamp!!.toLong(),
                gameReview.rating!!.toDouble()
            )

        impressions += game.userImpressions
    }

    private fun populateDetails() {
        title.text=game.title
        release.text=game.releaseDate
        genre.text=game.genre
        platform.text=game.platform
        esrb.text=game.esrbRating
        developer.text=game.developer
        publisher.text=game.publisher
        description.text=game.description
        val imageUrl = game.coverImage
        Picasso.get().load("https:"+imageUrl).fit().into(img);
        impressionsAdapter.updateUserImpressions(impressions)

    }
    @SuppressLint("SuspiciousIndentation")
    private fun getGameByTitle(name:String?):Game{
        if(name!=null)
        game = GameData.GetDetails(name)
        else game = GameData.GetDetails("Fortnite")
        return game
    }


}