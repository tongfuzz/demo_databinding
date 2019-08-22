package com.kk.tongfu.databinding

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView
import android.util.Log
import com.kk.tongfu.databinding.databinding.ActivitySpecialUseBinding
import com.kk.tongfu.databinding.entity.Address
import com.kk.tongfu.databinding.entity.ObservableUser

/**
 * Created by tongfu
 * on 2019-08-22
 * Desc:
 */

class SpecialUseActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding:ActivitySpecialUseBinding=DataBindingUtil.setContentView(this,R.layout.activity_special_use)

        val user=ObservableUser(ObservableField("tongfu"), ObservableInt(25), ObservableField("shanghai"))
        dataBinding.user=user
        dataBinding.btnChangeUserinfo.setOnClickListener{
            Log.e("onClick","changeUserName")
            user.userName.set("neza")
        }
    }
}