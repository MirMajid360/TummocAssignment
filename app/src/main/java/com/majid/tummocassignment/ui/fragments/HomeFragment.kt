package com.majid.tummocassignment.ui.fragments

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.majid.tummocassignment.common.hideVisibility
import com.majid.tummocassignment.common.showVisibility
import com.majid.tummocassignment.data.database.entity.Cart
import com.majid.tummocassignment.data.database.entity.CategoryWithItems
import com.majid.tummocassignment.data.database.entity.Favourite
import com.majid.tummocassignment.data.database.entity.Item
import com.majid.tummocassignment.databinding.FragmentHomeBinding
import com.majid.tummocassignment.ui.activities.MainActivity
import com.majid.tummocassignment.ui.adapters.CategoriesHomeAdapter
import com.majid.tummocassignment.ui.dialog.BUTTON
import com.majid.tummocassignment.ui.dialog.ConfirmDialog
import com.majid.tummocassignment.ui.dialog.ConfirmDialogModel
import com.majid.tummocassignment.ui.dialog.CustomDialogFragment
import com.majid.tummocassignment.utils.IListeners
import com.majid.tummocassignment.utils.STATUS_BAR_THEME
import com.majid.tummocassignment.utils.ViewUtils
import com.majid.tummocassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var categoriesAdapter: CategoriesHomeAdapter

    var categoriesList: ArrayList<CategoryWithItems> = ArrayList()

    private lateinit var categoriesNameList: java.util.ArrayList<String>
    @Inject
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewUtils.setupStatusBar(requireActivity(),STATUS_BAR_THEME.COLOR_THEME)

        categoriesNameList = java.util.ArrayList()
        getData()
        setListeners()
        initCategoriesAdapter()

        mainViewModel.getCategoryDate()
        mainViewModel.getFavourite()
        mainViewModel.getCartItems()

    }

    private fun setListeners() {
        binding.ivCartIcon.setOnClickListener {

            addFragment(CartFragment())
        }
        binding.ivFavouriteIcon.setOnClickListener {

            addFragment(FavouritesFragment())
        }

        binding.btnCategories.setOnClickListener {

            binding.btnCategories.hideVisibility()
            val dialog = CustomDialogFragment(categoriesNameList)
            dialog.setCloseButtonClickListener(View.OnClickListener {
                // Handle close button click here
                binding.btnCategories.showVisibility()
                // For example, dismiss the dialog or perform other actions
                dialog.dismiss()
            })

            dialog.show(childFragmentManager, "CustomDialogFragment")
        }

    }


//    private fun getItems() {
//        if (categoriesList.size >0){
//
//            for (c in categoriesList){
//
//                  c.items=   mainViewModel.categoriesLiveData(c.id)
//                    Log.w("CustomLog","${c.name}, ${c.id}, ${c.items.size}")
//
//            }
//
//
//        }
//    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        mainViewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer { items ->

            if (items.size > 0) {
                categoriesList.clear()
                categoriesList.addAll(items)
                for (c in categoriesList){
                    categoriesNameList.add(c.category.name)
                }
                categoriesAdapter.notifyDataSetChanged()
            }



        })

        mainViewModel.favouriteLiveData.observe(viewLifecycleOwner, Observer { list ->

            if (list.size > 0) {

            }
        })

        mainViewModel.cartLiveData.observe(viewLifecycleOwner, Observer { list ->
            var cartCount = 0
            if (list.size == 0) {
                binding.tvCartItemCount.hideVisibility()
            } else {
                binding.tvCartItemCount.showVisibility()


                for (c in list){
                    cartCount = cartCount+ c.quantity
                }
                binding.tvCartItemCount.text = cartCount.toString()
            }
        })

        mainViewModel.dataChanged.observe(viewLifecycleOwner, Observer { it ->

            mainViewModel.getFavourite()
            mainViewModel.getCartItems()
            mainViewModel.getCategoryDate()

        })

    }

    private fun initCategoriesAdapter() {


        categoriesAdapter =
            CategoriesHomeAdapter(
                requireContext(),
                categoriesList,
                object : IListeners.IProductClickListener {
                    override fun onItemClick(model: Any, pos: Int) {

                    }

                    override fun onAddClick(model: Any, pos: Int) {

                        if (model is Item) {

                            model.quantity = model.quantity+1
                            mainViewModel.updateItem(model)
                            val m = Cart()
                            m.categoryId = model.categoryId
                            m.id = model.id
                            m.name = model.name
                            m.icon = model.icon
                            m.price = model.price
                            m.quantity= model.quantity
                            mainViewModel.addItemToCart(m)
                        }
                    }

                    override fun onFavouriteClick(model: Any, pos: Int) {
                        if (model is Item) {
                            model.isFavourite = !model.isFavourite
                            mainViewModel.updateItem(model)
                            val m = Favourite()
                            m.categoryId = model.categoryId
                            m.id = model.id
                            m.name = model.name
                            m.icon = model.icon
                            m.price = model.price
                            m.isFavourite = model.isFavourite
                            m.quantity = model.quantity
                            mainViewModel.addItemToFavourite(m)
                        }
                    }
                })

        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = categoriesAdapter

    }


    fun addFragment(fragment: Fragment) {
        (activity as MainActivity?)?.openFragment(fragment, "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }
}