package com.kk.tongfu.databinding.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kk.tongfu.databinding.BR
import com.kk.tongfu.databinding.R
import com.kk.tongfu.databinding.databinding.AdapterUserBinding
import com.kk.tongfu.databinding.entity.User

/**
 * Created by tongfu
 * on 2019-08-23
 * Desc:
 */

class UserAdapter:RecyclerView.Adapter<UserAdapter.UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val dataBinding: AdapterUserBinding =DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.adapter_user,parent,false)
        return UserViewHolder(dataBinding)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        val user= User("tongfu",position,"shanghai")
        userViewHolder.dataBinding.setVariable(BR.user,user)
    }

    class UserViewHolder(val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root)
}