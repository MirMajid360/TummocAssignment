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
import com.majid.tummocassignment.databinding.FragmentCartBinding
import com.majid.tummocassignment.databinding.FragmentFavouritesBinding
import com.majid.tummocassignment.ui.adapters.CartAdapter
import com.majid.tummocassignment.ui.adapters.FavouritesAdapter
import com.majid.tummocassignment.ui.dialog.BUTTON
import com.majid.tummocassignment.ui.dialog.ConfirmDialog
import com.majid.tummocassignment.ui.dialog.ConfirmDialogModel
import com.majid.tummocassignment.utils.Converters
import com.majid.tummocassignment.utils.IListeners
import com.majid.tummocassignment.utils.STATUS_BAR_THEME
import com.majid.tummocassignment.utils.ViewUtils
import com.majid.tummocassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var cartAdapter: CartAdapter

    var itemList = ArrayList<Cart>()
    var subTotal = 0.0
    var discount = 0.0
    var total = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewUtils.setupStatusBar(requireActivity(), STATUS_BAR_THEME.WHITE_THEME)
        initView()
        getData()
        setListeners()
        initCartAdapter()

    }

    private fun initView() {
        binding.toolbar.tvTitle.text = "Cart"
    }

    private fun setListeners() {

        binding.toolbar.ivBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        binding.btnCheckOut.setOnClickListener {
            showDialog()
        }
    }

    private fun getData() {
        mainViewModel.getCartItems()
        mainViewModel.cartLiveData.observe(viewLifecycleOwner, Observer { list ->
            itemList.clear()
            itemList.addAll(list)

            for (i in itemList) {
                var itemCost = i.price?.times(i.quantity)
                subTotal = subTotal + itemCost!!

            }
            total = subTotal - discount
            binding.subTotalValue.text = subTotal.toString()
            binding.discountValue.text = discount.toString()
            binding.totalCost.text = Converters.formatPrice(total)
            cartAdapter.notifyDataSetChanged()
        })
        mainViewModel.dataChanged.observe(viewLifecycleOwner, Observer { it ->


            mainViewModel.getCartItems()

        })
    }

    private fun initCartAdapter() {


        cartAdapter =
            CartAdapter(
                requireContext(),
                itemList,
                object : IListeners.ICartItemClickListener {
                    override fun onItemClick(model: Any, pos: Int) {

                    }

                    override fun onAddClick(model: Any, pos: Int) {

                        if (model is Cart) {

                            model.quantity = model.quantity + 1
                            mainViewModel.addItemToCart(model)

                            val m = Item()
                            m.categoryId = model.categoryId
                            m.id = model.id
                            m.name = model.name
                            m.icon = model.icon
                            m.price = model.price
                            m.quantity = model.quantity
                            mainViewModel.updateItem(m)
                        }
                    }

                    override fun onRemoveClick(model: Any, pos: Int) {
                        if (model is Cart) {
                            model.quantity = model.quantity - 1

                            if (model.quantity >= 0) {

                                if (model.quantity >= 1) {
                                    // update cart
                                    mainViewModel.updateCart(model)
                                } else {
                                    mainViewModel.removeItemFromCart(model)
                                }


                                val m = Item()
                                m.categoryId = model.categoryId
                                m.id = model.id
                                m.name = model.name
                                m.icon = model.icon
                                m.price = model.price
                                m.quantity = model.quantity
                                mainViewModel.updateItem(m)
                            }
                        }
                    }

                })

        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = cartAdapter

    }

    private fun showDialog() {
        val model = ConfirmDialogModel()

        /**
         *         to show Status Dialog
         * */
        model.isShowStatus = true
        model.actionStatus = true
        model.message = "Your Order has been Placed !!"
        model.isCancellable = true
        model.isAutoHide =false
        model.autoHideDuration = 3000

        model.listener = object : ConfirmDialogModel.IDialogListener {
            override fun onButtonClicked(clickedButton: BUTTON) {

                if (clickedButton == BUTTON.POSITIVE) {
                    activity?.onBackPressedDispatcher?.onBackPressed()

                }

                if (clickedButton == BUTTON.NEGATIVE) {

                }

            }

        }
        val dialog = ConfirmDialog.newInstance(model)
        dialog.isCancelable = model.isCancellable
        dialog.show(childFragmentManager, "")


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CartFragment()
    }
}