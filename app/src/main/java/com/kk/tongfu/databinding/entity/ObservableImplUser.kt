package com.kk.tongfu.databinding.entity

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.kk.tongfu.databinding.BR

/**
 * Created by tongfu
 * on 2019-08-22
 * Desc: 定义可观察对象
 */

class ObservableImplUser : BaseObservable() {

    @get:Bindable
    var userName: String = ""
    set(value) {
        field=value
        notifyPropertyChanged(BR.userName)
    }

    @get:Bindable
    var userAge: Int = 0
    set(value) {
        field=value
        notifyPropertyChanged(BR.userAge)
    }

    @get:Bindable
    var userAddress: String = ""
    set(value){
        field=value
        notifyPropertyChanged(BR.userAddress)
    }


}