package com.ruben.composition.screens.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.RemoteViews
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.ruben.composition.R
import com.ruben.composition.components.BackButtonAppBar
import com.ruben.composition.ui.theme.Purple500

/**
 * Created by Ruben Quadros on 10/09/21
 **/
@Composable
fun NotificationScreen(
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    Surface(color = Purple500) {
        Scaffold(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding(),
            topBar = {
                BackButtonAppBar(
                    title = stringResource(id = R.string.notifications),
                    navigateBack = navigateBack
                )
            },
            content = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            //show the notification
                            showRequestNotification(context)
                        }) {
                        Text(text = stringResource(id = R.string.show_request_notification))
                    }

                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            //show the notification
                            showPauseNotification(context)
                        }) {
                        Text(text = stringResource(id = R.string.show_pause_notification))
                    }
                }
            }
        )
    }
}

fun showRequestNotification(context: Context) {

    createNotificationChannel(context)

    val notificationLayout = RemoteViews(context.packageName, R.layout.layout_stream_end_warning_notification)
    val description = context.getString(R.string.notification_desc)
    val start = description.indexOf(string = "LIVE", ignoreCase = false)
    val spannable = SpannableString(description)
    spannable.setLiveSpan(
        start, // start
        start+4, // end
        ContextCompat.getColor(context, R.color.purple_500)
    )
    notificationLayout.setTextViewText(
        R.id.tv_description, spannable
    )

    val builder = NotificationCompat.Builder(context, "Ruben").apply {
        setSmallIcon(R.drawable.ic_logo_notification)
        setCustomContentView(notificationLayout)
        setDefaults(Notification.DEFAULT_ALL)
        setSound(null)
        setAutoCancel(true)
        setLocalOnly(true)
        setOngoing(false)
    }

    with(NotificationManagerCompat.from(context)) {
        notify(1, builder.build())
    }

}

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Ruben Channel"
        val descriptionText = "This is a channel to show live now notification"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("Ruben", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun showPauseNotification(context: Context) {
    val builder = NotificationCompat.Builder(context, "Ruben").apply {
        setSmallIcon(R.drawable.ic_logo_notification)
        setDefaults(Notification.DEFAULT_ALL)
        setSound(null)
        setAutoCancel(true)
        setLocalOnly(true)
        setOngoing(false)
        setContentTitle("Stream paused")
        setContentText("Your Live Stream is still running and will be ended in 5 minutes if you do not return. Tap to get back to it now.")
        setStyle(NotificationCompat.BigTextStyle()
            .bigText("Your Live Stream is still running and will be ended in 5 minutes if you do not return. Tap to get back to it now."))
    }

    with(NotificationManagerCompat.from(context)) {
        notify(1, builder.build())
    }
}

fun SpannableString.setLiveSpan(start: Int, end: Int, color: Int) {
    this.setSpan(
        StyleSpan(Typeface.BOLD),
        start, // start
        end, // end
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )

    this.setSpan(
        ForegroundColorSpan(color),
        start, // start
        start+4, // end
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
}