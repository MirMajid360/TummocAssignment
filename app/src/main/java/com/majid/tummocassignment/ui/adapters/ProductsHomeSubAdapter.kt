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
import com.majid.tummocassignment.data.database.entity.Item
import com.majid.tummocassignment.databinding.ItemHomeAdapterBinding
import com.majid.tummocassignment.databinding.ItemProductBinding
import com.majid.tummocassignment.utils.Glide
import com.majid.tummocassignment.utils.IListeners


class ProductsHomeSubAdapter(
    var context: Context,
    var list: List<Item>,
    var listener: IListeners.IProductClickListener,
) : RecyclerView.Adapter<ProductsHomeSubAdapter.ProductsHomeSubViewHolder>() {
    inner class ProductsHomeSubViewHolder(val binding: ItemProductBinding) :
        ViewHolder(binding.root) {
        fun bind(model: Item,position: Int) {

            binding.tvProductName.text = model.name

            Glide.loadImage(model.icon.toString(),binding.ivProductImage)
            binding.tvPrice.text = "${context.getString(R.string.Rs)} ${model.price.toString()}"

            if (model.isFavourite){
                binding.ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_fill))
            }else{
                binding.ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))

            }

            binding.ivAddIcon.setOnClickListener {
                listener.onAddClick(model,position)
            }
            binding.favouriteHelper.setOnClickListener {
                listener.onFavouriteClick(model,position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHomeSubViewHolder {
        val itemBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductsHomeSubViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductsHomeSubViewHolder, position: Int) {

        holder.bind(list.get(position),position)


    }
}