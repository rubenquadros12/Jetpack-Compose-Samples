package com.ruben.composition

import android.content.Context
import android.widget.Toast

/**
 * Created by Ruben Quadros on 11/08/21
 **/
object Utility {
    
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}