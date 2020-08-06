package com.kishorenarang.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.kishorenarang.api.Article
import com.squareup.picasso.Picasso
import com.tarlochan.inshareapp.R
import java.text.SimpleDateFormat


class NewsAdapter(val articles:ArrayList<Article>, val context: Context?): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

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
        holder.open.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                context?.startActivity(intent)
            }

        })

        holder.share.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {

                val intent = Intent(Intent.ACTION_SEND)
                val shareBody = article.title+"\n\nRead the full article here.\n"+article.url +"\n\nNews Shared by: INShare App "
                intent.type = "text/plain"
              intent.putExtra(
                    Intent.EXTRA_SUBJECT,
                   article.title
                )
                intent.putExtra(Intent.EXTRA_TEXT, shareBody)
                context?.startActivity(intent)
            }

        })
        Picasso.get().load(article.image).into(holder.image)

    }
}