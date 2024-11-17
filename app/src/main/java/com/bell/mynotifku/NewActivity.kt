package com.bell.mynotifku

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bell.mynotifku.databinding.ActivityNewBinding

class NewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil nilai likeCount dan dislikeCount dari Intent
        val likeCount = intent.getIntExtra("like_count", 0)
        val dislikeCount = intent.getIntExtra("dislike_count", 0)

        // Update TextView di NewActivity
        binding.tvLikeCount.text = "Likes: $likeCount"
        binding.tvDislikeCount.text = "Dislikes: $dislikeCount"

        // Tombol Kembali ke MainActivity
        binding.btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)  // Menjalankan MainActivity
            finish()  // Menutup NewActivity agar tidak kembali lagi ke NewActivity
        }
    }
}


