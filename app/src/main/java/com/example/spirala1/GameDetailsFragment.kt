package com.example.spirala1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
    private lateinit var userImpressions: RecyclerView
    private lateinit var impressionsListAdapter: ImpressionsListAdapter
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
        val args = arguments
        var message = args?.getString("message")
        if (message != null) {
        //    sharedViewModel.isGameDetailsOpened.value=true
        }
        else message = "Fortnite"

            game = getGameByTitle(message)
            populateDetails()

            var userImpressionsList = game.userImpressions.sortedByDescending { it.timestamp }

            userImpressions = view.findViewById(R.id.reviews_list)
            userImpressions.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )

            impressionsListAdapter = ImpressionsListAdapter(listOf())
            userImpressions.adapter = impressionsListAdapter
            impressionsListAdapter.updateImpressions(userImpressionsList)


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
        val context: Context = img.context
        var id: Int = context.resources
            .getIdentifier(game.coverImage, "drawable", context.packageName)
        if (id===0) id=context.resources
            .getIdentifier("img", "drawable", context.packageName)
        img.setImageResource(id)
    }
    @SuppressLint("SuspiciousIndentation")
    private fun getGameByTitle(name:String?):Game{
        if(name!=null)
        game = GameData.GetDetails(name)
        else game = GameData.GetDetails("Fortnite")
        return game
    }


}