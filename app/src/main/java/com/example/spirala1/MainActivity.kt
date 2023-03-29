package com.example.spirala1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala1.GameData.Companion.GetDetails
import com.example.spirala1.GameData.Companion.getAll

class MainActivity : AppCompatActivity() {
    private lateinit var game1: Game
    private lateinit var home_button: Button
    private lateinit var details_button: Button
    private lateinit var search_button: Button
    private lateinit var game_list: RecyclerView
    private val lista = getAll()
    private lateinit var gameListAdapter: GamesListAdapter

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        home_button = findViewById(R.id.home_button)
        details_button = findViewById(R.id.details_button)
        search_button = findViewById(R.id.search_button)
        game_list = findViewById(R.id.game_list)
        game_list.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        gameListAdapter = GamesListAdapter(arrayListOf()) { game ->
            showGameDetails(game)
        }

        game_list.adapter = gameListAdapter
        gameListAdapter.updateGames(lista)
        val extras = intent.extras
        if (extras != null) {
            game1 = getGameByTitle(extras.getString("game_title", ""))
            if(game1.title!="Test")
            details_button.setOnClickListener {
                showGameDetails(game1)
            }
        }

    }

    private fun getGameByTitle(name:String):Game{
        game1 = GetDetails(name)
        return game1
    }
    private fun showGameDetails(game: Game) {
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game_title", game.title)
        }
        startActivity(intent)
    }
}