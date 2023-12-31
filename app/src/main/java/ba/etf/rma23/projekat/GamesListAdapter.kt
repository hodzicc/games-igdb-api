package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GamesListAdapter(
    private var games: List<Game>,
    private val onItemClicked: (game:Game) -> Unit
) : RecyclerView.Adapter<GamesListAdapter.GamesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder
    {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GamesViewHolder(view)
    }
    override fun getItemCount(): Int = games.size
    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        holder.gameTitle.text = games[position].title;
        holder.gameRating.text = games[position].rating.toString();
        holder.gamePlatform.text = games[position].platform;
        holder.gameRelease.text = games[position].releaseDate;
        holder.itemView.setOnClickListener{ onItemClicked(games[position]) }

    }
    fun updateGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }
    inner class GamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gameTitle by lazy { itemView.findViewById<TextView>(R.id.game_title_textview) }
        val gameRating by lazy { itemView.findViewById<TextView>(R.id.game_rating_textview) }
        val gamePlatform by lazy { itemView.findViewById<TextView>(R.id.game_platform_textview) }
        val gameRelease by lazy { itemView.findViewById<TextView>(R.id.game_release_date_textview) }
    }
}
