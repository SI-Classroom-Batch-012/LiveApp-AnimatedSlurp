package com.example.apicalls.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.apicalls.R
import com.example.apicalls.data.datamodels.Drink

class ItemAdapter(
    private val context: Context,
    var dataset: List<Drink>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Drink>) {
        dataset = list
        notifyDataSetChanged()
    }

    // der ViewHolder weiß welche Teile des Layouts beim Recycling angepasst werden
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.list_image)
        val textView = view.findViewById<TextView>(R.id.list_text)
        val cardView = view.findViewById<CardView>(R.id.list_card)
    }

    // hier werden neue ViewHolder erstellt
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        // das itemLayout wird gebaut
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        // und in einem ViewHolder zurückgegeben
        return ItemViewHolder(adapterLayout)
    }

    // hier findet der Recyclingprozess statt
    // die vom ViewHolder bereitgestellten Parameter werden verändert
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        holder.textView.text = item.name

        val color = if (item.clicked) {
            context.resources.getColor(R.color.salmon)
        } else {
            context.resources.getColor(R.color.light_salmon)
        }

        holder.cardView.setCardBackgroundColor(color)

        val imgUri = item.picture.toUri().buildUpon().scheme("https").build()

        holder.imageView.load(imgUri) {
            error(R.drawable.ic_round_broken_image_24)
            transformations(RoundedCornersTransformation(10f))
        }

        val animator = ObjectAnimator.ofFloat(holder.cardView, View.SCALE_Y, 0f, 1f)
        animator.duration = 800
        animator.start()

        holder.cardView.setOnClickListener {
            clickAnimation(it as CardView, item.clicked)
            item.clicked = !item.clicked
        }
    }

    // damit der LayoutManager weiß wie lang die Liste ist
    override fun getItemCount(): Int {
        return dataset.size
    }

    private fun clickAnimation(cardView: CardView, clicked: Boolean) {
        val rotator = ObjectAnimator.ofFloat(cardView, View.ROTATION_Y, 0f, 360f)
        rotator.duration = 600

        val color = if (clicked) {
            ContextCompat.getColor(context, R.color.light_salmon)
        } else {
            ContextCompat.getColor(context, R.color.salmon)
        }

        val colorizer = ObjectAnimator.ofArgb(
            cardView,
            "cardBackgroundColor",
            color
        )
        colorizer.duration = 700

        val set = AnimatorSet()
        set.playTogether(rotator, colorizer)
        set.start()
    }
}
