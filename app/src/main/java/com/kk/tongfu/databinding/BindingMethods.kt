package com.kk.tongfu.databinding

import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by tongfu
 * on 2019-08-23
 * Desc:
 */

@BindingMethods(value=[BindingMethod(type = TextView::class,attribute = "android:hint",method = "setText")])
class MyBindingMethods