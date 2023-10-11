package com.majid.tummocassignment.utils


class IListeners {


    interface IClickListeners {
        fun onItemClicked(model: Any, pos: Int)
    }

    interface IProductClickListener {
        fun onItemClick(model: Any, pos: Int)
        fun onAddClick(model: Any, pos: Int)
        fun onFavouriteClick(model: Any, pos: Int)
    }
    interface ICartItemClickListener {
        fun onItemClick(model: Any, pos: Int)
        fun onAddClick(model: Any, pos: Int)
        fun onRemoveClick(model: Any, pos: Int)
    }

    interface IDataChangeListener {
        fun onDataChanged()
    }
}