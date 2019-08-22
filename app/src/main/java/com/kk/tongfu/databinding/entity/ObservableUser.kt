package com.kk.tongfu.databinding.entity

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.location.Address

/**
 * Created by tongfu
 * on 2019-08-22
 * Desc:
 */

class ObservableUser(
    val userName: ObservableField<String>,
    val userAge: ObservableInt,
    val userAddress: ObservableField<String>
)