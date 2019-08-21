package com.kk.tongfu.databinding

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kk.tongfu.databinding.callbacks.MyHandlers
import com.kk.tongfu.databinding.databinding.ActivityMainBinding
import com.kk.tongfu.databinding.entity.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =DataBindingUtil.setContentView(this,R.layout.activity_main)
        //binding.user=User("tongfu",25,null)
        binding.user=null

        val map= mapOf(Pair("1","the first item in map"),Pair("2","the second item in map "), Pair("3","the thrid item in map"))
        val list= listOf("the first item in array","the second item in array","the thrid item in array")

        binding.map=map
        binding.list=list

        binding.index=1
        binding.key="3"

        binding.isRed=true

        binding.handler=MyHandlers()

    }
}
