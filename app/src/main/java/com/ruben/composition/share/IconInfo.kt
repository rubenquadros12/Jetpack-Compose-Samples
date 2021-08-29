package com.ruben.composition.share

import android.graphics.drawable.Drawable
import com.ruben.composition.R

/**
 * Created by Ruben Quadros on 20/08/21
 **/
data class IconInfo(var iconName: Int,
                    var drawableResourceString: Int? = null,
                    var drawableResource: Drawable? = null,
                    var addCircle: Boolean = false,
                    var toggleButton: Int? = null,
                    val textColor: Int = R.color.mv_white)
