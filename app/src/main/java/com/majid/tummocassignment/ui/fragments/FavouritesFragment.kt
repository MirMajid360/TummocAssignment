package com.majid.tummocassignment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.majid.tummocassignment.R
import com.majid.tummocassignment.data.database.entity.Cart
import com.majid.tummocassignment.data.database.entity.Favourite
import com.majid.tummocassignment.data.database.entity.Item
import com.majid.tummocassignment.databinding.FragmentFavouritesBinding
import com.majid.tummocassignment.databinding.FragmentHomeBinding
import com.majid.tummocassignment.ui.adapters.CategoriesHomeAdapter
import com.majid.tummocassignment.ui.adapters.FavouritesAdapter
import com.majid.tummocassignment.utils.IListeners
import com.majid.tummocassignment.utils.STATUS_BAR_THEME
import com.majid.tummocassignment.utils.ViewUtils
import com.majid.tummocassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FavouritesFragment : Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mainViewModel: MainViewModel
    lateinit var favouritesAdapter: FavouritesAdapter

    var favouritesList = ArrayList<Favourite>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewUtils.setupStatusBar(requireActivity(), STATUS_BAR_THEME.WHITE_THEME)
        initView()
        getData()
        setListeners()
        initFavouritesAdapter()

    }

    private fun initView() {
        binding.toolbar.tvTitle.text = "Favourites"
    }

    private fun setListeners() {

        binding.toolbar.ivBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getFavourite()
    }

    private fun getData() {

        mainViewModel.favouriteLiveData.observe(viewLifecycleOwner, Observer { list ->
            favouritesList.clear()
            favouritesList.addAll(list)
            favouritesAdapter.notifyDataSetChanged()
        })
        mainViewModel.dataChanged.observe(viewLifecycleOwner, Observer { it ->

            mainViewModel.getFavourite()


        })
    }

    private fun initFavouritesAdapter() {


        favouritesAdapter =
            FavouritesAdapter(
                requireContext(),
                favouritesList,
                object : IListeners.IProductClickListener {
                    override fun onItemClick(model: Any, pos: Int) {

                    }

                    override fun onAddClick(model: Any, pos: Int) {

                        if (model is Favourite) {

                            model.quantity = model.quantity + 1

                            val m = Cart()
                            m.categoryId = model.categoryId
                            m.id = model.id
                            m.name = model.name
                            m.icon = model.icon
                            m.price = model.price
                            m.quantity = model.quantity
                            mainViewModel.addItemToCart(m)

                        }
                    }

                    override fun onFavouriteClick(model: Any, pos: Int) {
                        if (model is Favourite) {
                            model.isFavourite = !model.isFavourite
                            mainViewModel.addItemToFavourite(model)
                            val m = Item()
                            m.categoryId = model.categoryId
                            m.id = model.id
                            m.name = model.name
                            m.icon = model.icon
                            m.price = model.price
                            m.isFavourite = model.isFavourite
                            m.quantity = model.quantity
                            mainViewModel.updateItem(m)
                        }
                    }
                })

        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = favouritesAdapter

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FavouritesFragment()
    }
}