package com.majid.tummocassignment.ui.dialog


enum class BUTTON{
    NEGATIVE,POSITIVE
}
class ConfirmDialogModel {
    var title=""
    var message=""
    var isCancellable = false
    var positiveButtonText=""
    var negativeButtonText=""
    var infoButtonText=""
    var isShowStatus=false
    var actionStatus=false
    var isAutoHide=false
    var autoHideDuration = 1000L
    var isDeleteDialog = false
    var showCategories = false
    lateinit var listener:IDialogListener

    interface IDialogListener{

        fun onButtonClicked(clickedButton: BUTTON)

    }
}