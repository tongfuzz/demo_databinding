package com.kk.tongfu.databinding.entity

/**
 * Created by tongfu
 * on 2019-08-20
 * Desc:
 */

data class User(val userName: String, val userAge: Int, val userAddress: String?) {

    companion object{
        @JvmStatic
        fun getEmail(): String {
            return "tongfus@126.com"
        }
    }

    fun print(info: String) {

    }
}