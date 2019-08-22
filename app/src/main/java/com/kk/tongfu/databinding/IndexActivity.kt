package com.kk.tongfu.databinding

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kk.tongfu.databinding.databinding.ActivityIndexBinding

/**
 * Created by tongfu
 * on 2019-08-22
 * Desc:
 */

class IndexActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding:ActivityIndexBinding=DataBindingUtil.setContentView(this,R.layout.activity_index)
        dataBinding.btnNormalUse.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
        dataBinding.btnSpecialUse.setOnClickListener{
            startActivity(Intent(this,SpecialUseActivity::class.java))
        }
    }
}