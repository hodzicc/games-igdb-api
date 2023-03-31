package com.example.spirala1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala1.GameData.Companion.GetDetails

class GameDetailsActivity : AppCompatActivity() {
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
    private lateinit var home: Button
    private lateinit var details: Button
    private lateinit var userImpressions: RecyclerView
    private lateinit var impressionsListAdapter: ImpressionsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)
        title = findViewById(R.id.game_title_textview)
        img = findViewById(R.id.cover_imageview)
        platform = findViewById(R.id.platform_textview)
        release = findViewById(R.id.release_date_textview)
        esrb = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        genre = findViewById(R.id.genre_textview)
        description = findViewById(R.id.description_textview)
        home = findViewById(R.id.home_button)
        details = findViewById(R.id.details_button)
        details.isEnabled=false
        home.setOnClickListener{
            showHome()
        }
        val extras = intent.extras
        if (extras != null) {
              game = getGameByTitle(extras.getString("game_title",""))
              populateDetails()
        } else {
            finish()
        }
    var userImpressionsList = game.userImpressions.sortedByDescending { it.timestamp }

    userImpressions = findViewById(R.id.reviews_list)
    userImpressions.layoutManager = LinearLayoutManager(
        this,
        LinearLayoutManager.HORIZONTAL,
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
private fun getGameByTitle(name:String):Game{
    game = GetDetails(name)
return game
}
    private fun showHome() {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("game_title", game.title)
        }
        startActivity(intent)
    }


}