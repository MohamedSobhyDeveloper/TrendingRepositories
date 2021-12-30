package com.aqwas.trendingrepositories.core.presentation.extensions

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.aqwas.trendingrepositories.R

fun Context.showToast(message: String, connectionError: Boolean=false){
    if (connectionError){
        Toast.makeText(this, getString(R.string.check_internet_connections), Toast.LENGTH_LONG).show()

    }else{
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }
}

fun Context.showGenericAlertDialog(message: String){
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton("ok"){ dialog, _ ->
             dialog.dismiss()
        }
    }.show()
}