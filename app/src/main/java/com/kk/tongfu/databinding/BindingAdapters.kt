package com.kk.tongfu.databinding

import android.databinding.BindingAdapter
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.widget.TextView

/**
 * Created by tongfu
 * on 2019-08-23
 * Desc:
 */
//@BindingMethods(value=[BindingMethod(type = TextView::class,attribute = "android:textColor",method = "setText")])
object BindingAdapters{

    @BindingAdapter("android:text")
    @JvmStatic fun setText(view:TextView,info:String){
        view.text = info+"haha"
    }
}