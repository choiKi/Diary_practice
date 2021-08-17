package com.ckh.diary_practice

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.ckh.diary_practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var changePassMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val numberPicker1 = binding.numberPicker1.apply {
            minValue=0
            maxValue = 9
        }
         val numberPicker2 = binding.numberPicker2.apply {
            minValue=0
            maxValue = 9
        }
        val numberPicker3 = binding.numberPicker3.apply {
            minValue=0
            maxValue = 9
        }
        binding.openBtn.setOnClickListener {
            if(changePassMode) {
                Toast.makeText(this,"비밀번호 변경중",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val passwordPreference = getSharedPreferences("password",Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            if(passwordPreference.getString("password","000").equals(passwordFromUser)){
                startActivity(Intent(this,DiaryActivity::class.java))
            }else {
                errorPopUp()
            }
        }
        binding.changePasswordBtn.setOnClickListener {
            val passwordPreference = getSharedPreferences("password",Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            if(changePassMode) {
                passwordPreference.edit(true) {
                    putString("password",passwordFromUser)
                    changePassMode = false
                    binding.changePasswordBtn.setBackgroundColor(Color.BLACK)
                }
            }else{
                if(passwordPreference.getString("password","000").equals(passwordFromUser)){
                    changePassMode = true
                    Toast.makeText(this,"변경할 비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
                    binding.changePasswordBtn.setBackgroundColor(Color.RED)
                }else {
                    errorPopUp()
                }
            }
        }
    }
    private fun errorPopUp() {
        AlertDialog.Builder(this)
            .setTitle("접속 실패")
            .setMessage("잘못된 비밀번호")
            .setPositiveButton("확인"){ _,_ ->}
            .create()
            .show()
    }
}