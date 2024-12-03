/*import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.coelib.egerton_university_app.MainActivity
import com.coelib.egerton_university_app.R
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Default Title"
        val body = inputData.getString("body") ?: "Default Body"
        val messageId = inputData.getString("messageId") ?: "defaultMessageId"
        val imageUrl = inputData.getString("imageUrl") ?: ""
        val link = inputData.getString("link") ?: ""

        sendNotification(applicationContext, title, body, messageId, imageUrl, link)
        return Result.success()
    }

    private fun sendNotification(
        context: Context,
        messageTitle: String,
        messageBody: String,
        messageId: String,
        imageUrl: String,
        link: String
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("message_id", messageId)
            putExtra("post_link", link)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            messageId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "your_dynamic_channel_id"
        val bitmap = if (imageUrl.isNotEmpty()) getBitmapFromURL(imageUrl) else null

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
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

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Dynamic Channel Title",
                NotificationManager.IMPORTANCE_HIGH
            )
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
}

 */
