package com.example.apicalls.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.apicalls.R
import com.example.apicalls.data.datamodels.Drink
import com.example.apicalls.databinding.ListItemBinding

class ItemAdapter(
    private var dataset: List<Drink>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Drink>) {
        dataset = list
        notifyDataSetChanged()
    }

    class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        val context = holder.binding.root.context
        val hText = holder.binding.listText
        val hImage = holder.binding.listImage
        val hCard = holder.binding.listCard

        hText.text = item.name

        var color = if (item.clicked) {
            ContextCompat.getColor(context, R.color.salmon)
        } else {
            ContextCompat.getColor(context, R.color.light_salmon)
        }
        hCard.setCardBackgroundColor(color)

        hImage.load(item.picture) {
            error(R.drawable.ic_round_broken_image_24)
            transformations(RoundedCornersTransformation(10f))
        }

        // ScaleY = Stretchen oben unten           AnimationsTyp, Start, Ende
        val animator = ObjectAnimator.ofFloat(hCard, View.SCALE_Y, 0f, 1f)
        animator.duration = 800
        animator.start()

        holder.binding.listCard.setOnClickListener {
            // RotationY = object horizontal rotieren                  Start, Ende
            val rotator = ObjectAnimator.ofFloat(hCard, View.ROTATION_Y, 0f, 360f)
            rotator.duration = 600

            color = if (item.clicked) {
                ContextCompat.getColor(context, R.color.light_salmon)
            } else {
                ContextCompat.getColor(context, R.color.salmon)
            }

            // OfArgb = farbe verändern
            val colorizer = ObjectAnimator.ofArgb(
                hCard,
                "cardBackgroundColor",
                color
            )
            colorizer.duration = 700
            
            // Zwei animationen auf einmal abspielen.
            val set = AnimatorSet()
            set.playTogether(rotator, colorizer)
            set.start()
            item.clicked = !item.clicked
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
