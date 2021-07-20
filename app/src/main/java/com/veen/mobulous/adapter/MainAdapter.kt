package com.veen.mobulous.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.veen.mobulous.R
import com.veen.mobulous.db.AddToCart
import com.veen.mobulous.db.CartDatabase
import com.veen.mobulous.model.Restaurant

class MainAdapter(private val context: Context, private val data: List<Restaurant>, private val savedcart: List<AddToCart>) :
        RecyclerView.Adapter<MainAdapter.ItemViewAbout> () {
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
            holder.viewButton.text = "Add"
            holder.viewHeading.text = HtmlCompat.fromHtml(data[position].restaurant.name, HtmlCompat.FROM_HTML_MODE_LEGACY)
            holder.viewSubHeading.text = HtmlCompat.fromHtml(data[position].restaurant.mezzo_provider, HtmlCompat.FROM_HTML_MODE_LEGACY)
            Picasso.get().load(data[position].restaurant.thumb).into(holder.viewImage)

            holder.viewButton.setOnClickListener {
                val saveimage = data[position].restaurant.thumb
                val savename = data[position].restaurant.name
                val savediscription = data[position].restaurant.mezzo_provider
                val saveid = data[position].restaurant.id
                val saveamount = data[position].restaurant.price_range
                CartDatabase(context.applicationContext).getNoteDao().addToCart(AddToCart(saveimage, savename, savediscription,saveamount.toString(),saveid))
                Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}