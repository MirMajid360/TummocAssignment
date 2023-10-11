package com.majid.tummocassignment.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majid.tummocassignment.R
import com.majid.tummocassignment.common.toggleVisibility
import com.majid.tummocassignment.data.database.entity.Category
import com.majid.tummocassignment.data.database.entity.CategoryWithItems
import com.majid.tummocassignment.data.database.entity.Item
import com.majid.tummocassignment.databinding.ItemHomeAdapterBinding
import com.majid.tummocassignment.utils.IListeners


class CategoriesHomeAdapter(
    var context: Context,
    var list: ArrayList<CategoryWithItems>,
    var listener: IListeners.IProductClickListener,
) : RecyclerView.Adapter<CategoriesHomeAdapter.CategoriesViewHolder>() {
    inner class CategoriesViewHolder(val binding: ItemHomeAdapterBinding) :
        ViewHolder(binding.root) {
        fun bind(model: CategoryWithItems) {

            binding.tvTitle.text = model.category.name

            initSubAdapter(model.items)
            var isImageVisible = binding.recyclerView.isVisible
            binding.ivDropdownIcon.setOnClickListener {
                com.majid.tummocassignment.animations.AnimationUtils.animateDropDownIcon(
                    binding.ivDropdownIcon,
                    isImageVisible
                )
                if (isImageVisible) {
                    binding.recyclerView.visibility = View.VISIBLE
                    val slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)

                    binding.recyclerView.startAnimation(slideDown)
                } else {
                    val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
                    binding.recyclerView.startAnimation(slideUp)
                    binding.recyclerView.visibility = View.GONE
                }
                isImageVisible = !isImageVisible
            }
        }

        private fun initSubAdapter(list: List<Item>) {


            val productsHomeSubAdapter =
                ProductsHomeSubAdapter(
                    context,
                    list,
                    object : IListeners.IProductClickListener {
                        override fun onItemClick(model: Any, pos: Int) {

                            listener.onItemClick(model,pos)

                        }

                        override fun onAddClick(model: Any, pos: Int) {

                            listener.onAddClick(model,pos)
                        }

                        override fun onFavouriteClick(model: Any, pos: Int) {

                            listener.onFavouriteClick(model,pos)
                        }
                    })

            var layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = productsHomeSubAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemBinding =
            ItemHomeAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoriesViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {

        holder.bind(list.get(position))


    }
}