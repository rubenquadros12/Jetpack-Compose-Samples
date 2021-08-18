package com.ruben.composition

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.ruben.composition.ui.theme.CompositionTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requestWindowFeature(Window.FEATURE_NO_TITLE)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                CompositionTheme {
                    CompositionApp()
                }
            }
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window?.insetsController?.hide(
//                WindowInsets.Type.statusBars()
//                        or WindowInsets.Type.displayCutout()
//                        or WindowInsets.Type.ime()
//                        or WindowInsets.Type.systemGestures()
//                        or WindowInsets.Type.mandatorySystemGestures()
//                        //or WindowInsets.Type.navigationBars()
//                        //or WindowInsets.Type.systemBars()
////                        or WindowInsets.Type.ime()
//            )
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
    }
}