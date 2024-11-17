package com.bell.mynotifku

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotifReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        val sharedPreferences = context.getSharedPreferences("notif_prefs", Context.MODE_PRIVATE)
        val likeCount = sharedPreferences.getInt("like_count", 0)
        val dislikeCount = sharedPreferences.getInt("dislike_count", 0)

        when (action) {
            "ACTION_LIKE" -> {
                val newLikeCount = likeCount + 1
                sharedPreferences.edit().putInt("like_count", newLikeCount).apply()

                // Tampilkan Toast untuk aksi Like
                Toast.makeText(context, "Like clicked! Total Likes: $newLikeCount", Toast.LENGTH_SHORT).show()

                // Pindah ke NewActivity
                val likeIntent = Intent(context, NewActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("like_count", newLikeCount)
                    putExtra("dislike_count", dislikeCount)
                }
                context.startActivity(likeIntent)
            }

            "ACTION_DISLIKE" -> {
                val newDislikeCount = dislikeCount + 1
                sharedPreferences.edit().putInt("dislike_count", newDislikeCount).apply()

                // Tampilkan Toast untuk aksi Dislike
                Toast.makeText(context, "Dislike clicked! Total Dislikes: $newDislikeCount", Toast.LENGTH_SHORT).show()

                // Pindah ke NewActivity
                val dislikeIntent = Intent(context, NewActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("like_count", likeCount)
                    putExtra("dislike_count", newDislikeCount)
                }
                context.startActivity(dislikeIntent)
            }
        }
    }
}
