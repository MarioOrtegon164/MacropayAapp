package com.macropay.prueba.utils

import android.annotation.SuppressLint
import android.app.Activity
import cn.pedant.SweetAlert.SweetAlertDialog
import java.text.SimpleDateFormat
import java.util.Date

class Utils {
    fun showErrorDialog(activity:Activity,message:String) {
        SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Error")
            .setContentText(message)
            .setConfirmText("Aceptar")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }.show()
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateString(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("MMM dd, yyyy")

        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }
}