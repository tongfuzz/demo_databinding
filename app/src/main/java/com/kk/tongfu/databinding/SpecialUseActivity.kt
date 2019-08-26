package com.kk.tongfu.databinding

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kk.tongfu.databinding.databinding.ActivitySpecialUseBinding
import com.kk.tongfu.databinding.entity.ObservableImplUser
import kotlinx.android.synthetic.main.activity_special_use.*

/**
 * Created by tongfu
 * on 2019-08-22
 * Desc:
 */

class SpecialUseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding: ActivitySpecialUseBinding = DataBindingUtil.setContentView(this, R.layout.activity_special_use)


        ActivitySpecialUseBinding.inflate(layoutInflater)
        //val user = ObservableUser(ObservableField("tongfu"), ObservableInt(25), ObservableField("shanghai"))
        /* val user = ObservableArrayMap<String, Any>().apply {
             put("userName", "tongfu")
             put("userAge", 10)
             put("userAddress", "shanghai")
             put("userClass", "3-10")
         }
         dataBinding.user = user

         dataBinding.btnChangeUserinfo.setOnClickListener {
             Log.e("onClick", "changeUserName")
             //user.userName="neza"
             user.put("userName","neza")
         }*/

        val user = ObservableImplUser()
        user.userName="tongfu"
        user.userAge=25
        user.userAddress="shanghai"
        dataBinding.user = user


        dataBinding.btnChangeUserinfo.setOnClickListener {
            Log.e("onClick", "changeUserName")
            //user.userName="neza"
            user.userName = "tongfu"
        }


        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.framelayout,BindingUseFragment.instance,"bindingUserFragment")
        transaction.show(BindingUseFragment.instance)
        transaction.commitAllowingStateLoss()
    }
}