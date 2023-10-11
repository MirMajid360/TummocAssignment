package com.majid.tummocassignment.ui.dialog

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.DialogFragment

import com.majid.tummocassignment.R
import com.majid.tummocassignment.common.hideVisibility
import com.majid.tummocassignment.common.showVisibility
import com.majid.tummocassignment.databinding.FragmentConfirmDialogBinding


class ConfirmDialog(val dialogModel: ConfirmDialogModel) : DialogFragment() {
    lateinit var binding: FragmentConfirmDialogBinding

    private lateinit var categoriesListView: ListView
    private lateinit var categoriesNameList: java.util.ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentConfirmDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    }

    fun initViews() {


        if (dialogModel.isShowStatus) {
            binding.statusLayout.showVisibility()
            binding.statusIocn.showVisibility()
            binding.statusMessage.text = dialogModel.message
            if (dialogModel.actionStatus) {
                binding.statusIocn.setImageDrawable(resources.getDrawable(R.drawable.ic_green_check,null))
            } else {
                binding.statusIocn.setImageDrawable(resources.getDrawable(R.drawable.ic_error,null))
            }
            if (dialogModel.isAutoHide) {
                Handler(Looper.myLooper()!!).postDelayed({
                    dismiss()
                }, dialogModel.autoHideDuration)
            }

            binding.btnPositive.setOnClickListener {
                dialogModel.listener.onButtonClicked(BUTTON.POSITIVE)
                dismiss()
            }

        }

        if (dialogModel.showCategories){
            binding.statusLayout.hideVisibility()
            binding.categoriesViewParent.showVisibility()

        }

        binding.categoriesView.close.setOnClickListener {
            dialogModel.listener.onButtonClicked(BUTTON.POSITIVE)
            dismiss()
        }


    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(model: ConfirmDialogModel) =
            ConfirmDialog(model)

    }
}
