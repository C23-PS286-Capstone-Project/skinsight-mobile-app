package com.dayatmuhammad.skinsight.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.dayatmuhammad.skinsight.R

class LoadingDialog(
    private val mActivity: Activity
    ) {
    private lateinit var isLoading: AlertDialog

    fun startLoading(){
        val inflater = mActivity.layoutInflater
        val loadingView = inflater.inflate(R.layout.loading_layout, null)
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(loadingView)
        builder.setCancelable(false)
        isLoading = builder.create()
        isLoading.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isLoading.show()
    }

    fun stopLoading(){
        isLoading.dismiss()
    }
}
