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
import com.majid.tummocassignment.data.database.entity.Cart
import com.majid.tummocassignment.data.database.entity.Category
import com.majid.tummocassignment.data.database.entity.Favourite
import com.majid.tummocassignment.data.database.entity.Item
import com.majid.tummocassignment.databinding.ItemCartBinding
import com.majid.tummocassignment.databinding.ItemHomeAdapterBinding
import com.majid.tummocassignment.databinding.ItemProductBinding
import com.majid.tummocassignment.utils.Converters
import com.majid.tummocassignment.utils.Glide
import com.majid.tummocassignment.utils.IListeners


class CartAdapter(
    var context: Context,
    var list: List<Cart>,
    var listener: IListeners.ICartItemClickListener,
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    inner class CartViewHolder(val binding: ItemCartBinding) :
        ViewHolder(binding.root) {
        fun bind(model: Cart,position: Int) {

            binding.tvProductName.text = model.name

            Glide.loadImage(model.icon.toString(),binding.ivProductImage)
            binding.tvPrice.text = "${context.getString(R.string.Rs)} ${model.price.toString()}"

            var totalPrice = model.price
            if (model.quantity> 0){
                totalPrice = model.price?.times(model.quantity)
            }

            binding.tvTotalPrice.text = Converters.formatPrice(totalPrice!!)

            binding.tvQuantity.text = model.quantity.toString()

            binding.ivAddIcon.setOnClickListener {
                listener.onAddClick(model,position)
            }
            binding.ivRemoveIcon.setOnClickListener {
                listener.onRemoveClick(model,position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemBinding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CartViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        holder.bind(list.get(position),position)


    }
}