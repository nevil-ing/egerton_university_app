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
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.coelib.egerton_university_app.MainActivity
import com.coelib.egerton_university_app.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            // Check if data needs to be processed by long-running job
            if (needsToBeScheduled()) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }

            val title = remoteMessage.data["Title"] ?: "Default Title"
            val intro = remoteMessage.data["Intro"] ?: "Default Intro"
            val imageUrl = remoteMessage.data["Image_url"] ?: ""
            val link = remoteMessage.data["Link"] ?: ""
            val messageId = remoteMessage.data["MessageId"] ?: "Default messageId"

            // Pass imageUrl and link to the notification
            sendNotification(title, intro, messageId, imageUrl, link)
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.title ?: "default title", it.body ?: "default body", it.channelId ?: "default body", "", "")
        }
    }
    // [END receive_message]

    private fun needsToBeScheduled() = true

    // [START on_new_token]
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .build()
        WorkManager.getInstance(this)
            .beginWith(work)
            .enqueue()
        // [END dispatch_job]
    }

    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    // Updated sendNotification function with image and link handling
    private fun sendNotification(messageTitle: String, messageBody: String, messageId: String, imageUrl: String, link: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("message_id", messageId) // Pass message ID for handling in the activity
            putExtra("post_link", link) // Pass the link to the post
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            messageId.hashCode(), // Unique ID based on message ID
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "your_dynamic_channel_id"

        // Download the image and set it in the notification if available
        val bitmap = if (imageUrl.isNotEmpty()) getBitmapFromURL(imageUrl) else null

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher) // Set small icon
            .setContentTitle(messageTitle) // Title of the notification
            .setContentText(messageBody) // Short text of the notification
            .setStyle(
                if (bitmap != null) {
                    NotificationCompat.BigPictureStyle() // Big picture style for the image
                        .bigPicture(bitmap)

                        .setSummaryText(messageBody)
                } else {
                    NotificationCompat.BigTextStyle().bigText(messageBody) // Fallback to BigTextStyle
                }
            )
            .setAutoCancel(true) // Automatically cancel the notification when clicked
            .setContentIntent(pendingIntent) // Set the pending intent to open the post
           // .setColor(resources.getColor(R., theme)) // Set a Material 3 greenish color
            .setPriority(NotificationCompat.PRIORITY_HIGH) // High priority for the notification

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Dynamic Channel Title",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel description"
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(messageId.hashCode(), notificationBuilder.build())
    }

    // Helper function to download the image from the URL
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

    internal class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
        override fun doWork(): Result {
            // TODO(developer): add long running task here.
            return Result.success()
        }
    }
}
