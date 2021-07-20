package com.veen.mobulous.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.veen.mobulous.R
import com.veen.mobulous.Utlis.PageRefresh
import com.veen.mobulous.db.AddToCart
import com.veen.mobulous.db.CartDatabase
import com.veen.mobulous.model.Restaurant

class FileAdapter(private val context: Context, private val savedcart: List<AddToCart>, private val pageRefresh: PageRefresh) :
    RecyclerView.Adapter<FileAdapter.ItemViewAbout> () {
    inner class ItemViewAbout(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewImage = itemView.findViewById<ImageView>(R.id.imageView)
        val viewButton = itemView.findViewById<Button>(R.id.addtodb)
        val viewcard = itemView.findViewById<LinearLayout>(R.id.cardlayout)
        val viewHeading = itemView.findViewById<TextView>(R.id.heading)
        val viewSubHeading = itemView.findViewById<TextView>(R.id.subheading)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewAbout {
        var view: View = LayoutInflater.from(context).inflate(R.layout.view, parent, false)
        return ItemViewAbout(view)
    }

    override fun onBindViewHolder(holder: ItemViewAbout, position: Int) {
        try {
            holder.viewButton.text = "Delete"
            holder.viewHeading.text = HtmlCompat.fromHtml(savedcart[position].name, HtmlCompat.FROM_HTML_MODE_LEGACY)
            holder.viewSubHeading.text = HtmlCompat.fromHtml(savedcart[position].discription, HtmlCompat.FROM_HTML_MODE_LEGACY)
            Picasso.get().load(savedcart[position].image).into(holder.viewImage)


            holder.viewButton.setOnClickListener {
                val saveid = savedcart[position].id
                CartDatabase(context.applicationContext).getNoteDao().deleteByTitle(saveid)
                Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show()
                pageRefresh.OnUpdatePage()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return savedcart.size
    }
}