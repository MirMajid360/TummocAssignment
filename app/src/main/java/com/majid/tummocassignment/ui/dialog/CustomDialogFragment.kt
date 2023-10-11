package com.majid.tummocassignment.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import com.majid.tummocassignment.R

class CustomDialogFragment(val list :ArrayList<String>) : DialogFragment() {

    private var closeButtonClickListener: View.OnClickListener? = null

    fun setCloseButtonClickListener(listener: View.OnClickListener) {
        closeButtonClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_content, container, false)

        // Get references to ListView and the close button
        val listView = view.findViewById<ListView>(R.id.listView)
        val closeButton = view.findViewById<ImageView>(R.id.close)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)



        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter
        // Handle close button click
        closeButton.setOnClickListener {
            closeButtonClickListener?.onClick(it)
            dismiss()
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }
}
