package com.kk.tongfu.databinding

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kk.tongfu.databinding.databinding.ActivityMainBinding
import com.kk.tongfu.databinding.entity.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.user=User("tongfu",25,"shanghai")
    }
}
