package com.example.spirala1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImpressionsListAdapter(
    internal var list: List<UserImpression>
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val VIEW_TYPE_ONE = 1
    internal val VIEW_TYPE_TWO = 2

    private inner class ViewHolder1 internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val username: TextView = itemView.findViewById(R.id.username_textview)
        val ratingbar: RatingBar = itemView.findViewById(R.id.rating_bar)
    }

    private inner class ViewHolder2 internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

     val username: TextView = itemView.findViewById(R.id.username_textview)
     val review: TextView = itemView.findViewById(R.id.review_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ONE) {
            ViewHolder1(LayoutInflater.from(parent.context).inflate(R.layout.item_rating, parent, false))
        } else ViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.item_description,parent,false)) //if it's not VIEW_TYPE_ONE then its VIEW_TYPE_TWO
   }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position] is UserRating) {
            (holder as ViewHolder1).username.text=(list[position] as UserRating).username
            (holder as ViewHolder1).ratingbar.rating= (list[position] as UserRating).rating.toFloat()

        } else {
            (holder as ViewHolder2).username.text=(list[position] as UserReview).username
            (holder as ViewHolder2).review.text= (list[position] as UserReview).review
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is UserRating) {
            VIEW_TYPE_ONE
        } else VIEW_TYPE_TWO
    }
    fun updateImpressions(list: List<UserImpression>) {
        this.list = list
        notifyDataSetChanged()
    }
}
