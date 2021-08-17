package com.ckh.diary_practice

import android.content.Context
import android.icu.text.Normalizer2
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.ckh.diary_practice.databinding.ActivityDiaryBinding
import com.ckh.diary_practice.databinding.ActivityMainBinding

class DiaryActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDiaryBinding.inflate(layoutInflater) }
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val detailPreferences = getSharedPreferences("detail",Context.MODE_PRIVATE)

        binding.diary.setText(detailPreferences.getString("detail",""))

        val runnable = Runnable {
            getSharedPreferences("diary",Context.MODE_PRIVATE).edit {
                putString("detail",binding.diary.text.toString())
            }
        }
        binding.diary.addTextChangedListener {
            //text가 변경될때마다 실행됨
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable,500)
        }
    }
}