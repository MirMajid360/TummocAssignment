package com.majid.tummocassignment.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majid.tummocassignment.R
import com.majid.tummocassignment.animations.AnimationUtils
import com.majid.tummocassignment.data.database.entity.Category
import com.majid.tummocassignment.data.database.entity.Favourite
import com.majid.tummocassignment.data.database.entity.Item
import com.majid.tummocassignment.databinding.ItemFavouriteBinding
import com.majid.tummocassignment.databinding.ItemHomeAdapterBinding
import com.majid.tummocassignment.databinding.ItemProductBinding
import com.majid.tummocassignment.utils.Glide
import com.majid.tummocassignment.utils.IListeners


class FavouritesAdapter(
    var context: Context,
    var list: List<Favourite>,
    var listener: IListeners.IProductClickListener,
) : RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {
    inner class FavouritesViewHolder(val binding: ItemFavouriteBinding) :
        ViewHolder(binding.root) {
        fun bind(model: Favourite,position: Int) {

            binding.tvProductName.text = model.name

            Glide.loadImage(model.icon.toString(),binding.ivProductImage)
            binding.tvPrice.text = "${context.getString(R.string.Rs)} ${model.price.toString()}"

            binding.tvQuantity.text = model.quantity.toString()
            if (model.isFavourite){
                binding.ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_fill))
            }else{
                binding.ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))

            }

            binding.ivAddIcon.setOnClickListener {
                listener.onAddClick(model,position)
            }
            binding.ivFavouriteIcon.setOnClickListener {
                listener.onFavouriteClick(model,position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val itemBinding =
            ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavouritesViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {

        holder.bind(list.get(position),position)


    }
}