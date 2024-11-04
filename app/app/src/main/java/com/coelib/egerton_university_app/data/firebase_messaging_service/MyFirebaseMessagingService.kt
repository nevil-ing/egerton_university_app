package com.coelib.egerton_university_app.data.firebase_messaging_service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.coelib.egerton_university_app.MainActivity
import com.coelib.egerton_university_app.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data["Title"] ?: "Default Title"
            val intro = remoteMessage.data["Intro"] ?: "Default Intro"
            val imageUrl = remoteMessage.data["Image_url"] ?: ""
            val link = remoteMessage.data["Link"] ?: ""
            val messageId = remoteMessage.data["MessageId"] ?: "Default messageId"

            sendNotification(title, intro, messageId, imageUrl, link)
        }

        remoteMessage.notification?.let {
            sendNotification(it.title ?: "Default Title", it.body ?: "Default Body", it.channelId ?: "defaultChannel", "", "")
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    private fun sendNotification(messageTitle: String, messageBody: String, messageId: String, imageUrl: String, link: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("message_id", messageId)
            putExtra("post_link", link)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            messageId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "your_dynamic_channel_id"
        val bitmap = if (imageUrl.isNotEmpty()) getBitmapFromURL(imageUrl) else null

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setStyle(
                if (bitmap != null) {
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .setSummaryText(messageBody)
                } else {
                    NotificationCompat.BigTextStyle().bigText(messageBody)
                }
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Dynamic Channel Title",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(messageId.hashCode(), notificationBuilder.build())
    }

    private fun getBitmapFromURL(imageUrl: String): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}
