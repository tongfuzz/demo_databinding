package com.kk.tongfu.databinding.callbacks

import android.util.Log
import android.view.View
import android.widget.TextView

/**
 * Created by tongfu
 * on 2019-08-21
 * Desc:
 */

class MyHandlers {

        fun onTextViewClick(view: View){
                (view as TextView).text="点击了"
        }

        fun onTextViewClick2(view:View,name:String):String{
                (view as TextView).text=name
                return name
        }

        fun onCheckBoxStatusChange(info:String,isChecked:Boolean){
                Log.e("onCheckBoxStatusChange",isChecked.toString())
        }

}