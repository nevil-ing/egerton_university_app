package com.coelib.egerton_university_app.utils.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.ExistingPeriodicWorkPolicy
import com.coelib.egerton_university_app.MainActivity
import com.coelib.egerton_university_app.R
import java.util.concurrent.TimeUnit

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        showNotification(applicationContext)
        return Result.success()
    }

    private fun showNotification(context: Context) {
        val channelId = "periodic_channel_id"
        val title = "Scheduled Notification"
        val messageBody = "This notification is triggered every 12 hours."

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Scheduled Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(1, notificationBuilder.build())
    }
}

fun scheduleNotificationWorker(context: Context) {
    val notificationRequest = PeriodicWorkRequestBuilder<NotificationWorker>(12, TimeUnit.HOURS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "NotificationWork",
        ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
        notificationRequest
    )
}
