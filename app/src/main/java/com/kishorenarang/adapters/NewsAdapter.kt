package com.kishorenarang.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kishorenarang.api.Article
import com.squareup.picasso.Picasso
import com.tarlochan.inshareapp.R
import kotlinx.android.synthetic.main.news_card.view.*
import java.text.SimpleDateFormat

class NewsAdapter(val articles:ArrayList<Article>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val image = view.findViewById<ImageView>(R.id.card_image)
        val title = view.findViewById<TextView>(R.id.card_title)
        val author = view.findViewById<TextView>(R.id.card_author)
        val date = view.findViewById<TextView>(R.id.card_date)
        val description = view.findViewById<TextView>(R.id.card_description)
        val open = view.findViewById<ImageButton>(R.id.card_open)
        val share = view.findViewById<ImageButton>(R.id.card_share)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.news_card,parent,false)
        return ViewHolder(cardView)

    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val formatter = SimpleDateFormat("dd MMM YYYY");
        val article = articles.get(position)
        holder.author.text = article.author
        holder.date.text = formatter.format(article.date)
        holder.title.text = article.title
        holder.description.text = article.description
        Picasso.get().load(article.image).into(holder.image)

    }
}