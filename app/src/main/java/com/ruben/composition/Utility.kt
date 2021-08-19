package com.ruben.composition

import android.content.Context
import android.widget.Toast
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Created by Ruben Quadros on 11/08/21
 **/
object Utility {
    
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Long.parseCount(): String {
    val df = DecimalFormat("#.#").apply { roundingMode = RoundingMode.HALF_DOWN }
    return if (this < 1000)
        this.toString()
    else if (this < 1000000) {
        if (this in 1100..9999)
            df.format(this.div(1000f).toBigDecimal()) + "K"
        else
            (this.div(1000)).toString() + "K"
    } else if (this < 1000000000) {
        if (this in 1100000..9999999)
            df.format(this.div(1000000f).toBigDecimal()) + "M"
        else
            (this.div(1000000)).toString() + "M"
    } else {
        if (this in 1100000000..9999999999)
            df.format(this.div(1000000000f).toBigDecimal()) + "B"
        else
            (this.div(1000000000)).toString() + "B"
    }
}