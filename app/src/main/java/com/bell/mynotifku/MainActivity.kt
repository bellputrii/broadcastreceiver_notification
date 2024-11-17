package com.bell.mynotifku

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bell.mynotifku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val channelId = "TEST_NOTIF"
    private val notifId = 90

    private val notifManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        binding.btnNotif.setOnClickListener {
            Log.d("Notification", "Button 1 clicked, sending notification") // Log for debugging
            sendNotificationWithAction()
        }

        binding.btnUpdate.setOnClickListener {
            Log.d("Notification", "Button 2 clicked, sending notification with image") // Log for debugging
            sendNotificationWithImage()
        }
    }

    private fun sendNotificationWithAction() {
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }

        // PendingIntent untuk aksi Like
        val likeIntent = Intent(this, NotifReceiver::class.java).apply {
            action = "ACTION_LIKE"
        }
        val likePendingIntent = PendingIntent.getBroadcast(this, 1, likeIntent, flag)

        // PendingIntent untuk aksi Dislike
        val dislikeIntent = Intent(this, NotifReceiver::class.java).apply {
            action = "ACTION_DISLIKE"
        }
        val dislikePendingIntent = PendingIntent.getBroadcast(this, 2, dislikeIntent, flag)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_bell)
            .setContentTitle("First Notification")
            .setContentText("Apakah kamu enjoy ngoding kotlin?")
            .addAction(R.drawable.like, "Like", likePendingIntent)
            .addAction(R.drawable.dislike, "Dislike", dislikePendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        Log.d("Notification", "Notification 1 ready to be sent")
        notifManager.notify(notifId + 1, builder.build())  // Display Notification 1
    }

    private fun sendNotificationWithImage() {
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }

        // PendingIntent untuk aksi Like
        val likeIntent = Intent(this, NotifReceiver::class.java).apply {
            action = "ACTION_LIKE"
        }
        val likePendingIntent = PendingIntent.getBroadcast(this, 1, likeIntent, flag)

        // PendingIntent untuk aksi Dislike
        val dislikeIntent = Intent(this, NotifReceiver::class.java).apply {
            action = "ACTION_DISLIKE"
        }
        val dislikePendingIntent = PendingIntent.getBroadcast(this, 2, dislikeIntent, flag)

        // Gambar untuk notifikasi
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.img)  // Gambar yang akan ditampilkan
        Log.d("Notification", "Bitmap loaded successfully")  // Log to confirm bitmap loading

        // Create the notification with the big picture style
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_bell)
            .setContentTitle("Second Notification")
            .setContentText("Apakah kamu bersenang - senang dengan kotlin?")
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null as Bitmap?))  // Cast null to Bitmap explicitly
            .addAction(R.drawable.like, "Like", likePendingIntent)
            .addAction(R.drawable.dislike, "Dislike", dislikePendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        Log.d("Notification", "Notification 2 ready to be sent")
        notifManager.notify(notifId + 2, builder.build())  // Display Notification 2
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel 1", NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Test Channel"
            }
            Log.d("Notification", "Notification Channel Created") // Log to confirm the channel creation
            notifManager.createNotificationChannel(channel)
        }
    }
}
