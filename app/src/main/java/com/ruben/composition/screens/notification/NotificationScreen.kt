package com.ruben.composition.screens.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
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
                            showNotification(context)
                        }) {
                        Text(text = stringResource(id = R.string.show_notification))
                    }
                }
            }
        )
    }
}

fun showNotification(context: Context) {
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Ruben Channel"
            val descriptionText = "This is a channel to show live now notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Ruben", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    createNotificationChannel()

    val notificationLayout = RemoteViews(context.packageName, R.layout.layout_stream_end_warning_notification)

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