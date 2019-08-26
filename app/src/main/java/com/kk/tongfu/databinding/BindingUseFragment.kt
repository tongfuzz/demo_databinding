package com.kk.tongfu.databinding

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kk.tongfu.databinding.adapter.UserAdapter
import com.kk.tongfu.databinding.databinding.FragmentBindinguseBinding
import com.kk.tongfu.databinding.entity.User
import kotlinx.android.synthetic.main.fragment_bindinguse.*

/**
 * Created by tongfu
 * on 2019-08-23
 * Desc:
 */

class BindingUseFragment :Fragment() {

     lateinit var binding:FragmentBindinguseBinding

    companion object{
        val instance:BindingUseFragment by lazy(mode=LazyThreadSafetyMode.SYNCHRONIZED) {
            BindingUseFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflater.inflate(R.layout.fragment_bindinguse,container,false)
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_bindinguse,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.user=User("tongfu",25,"shanghai")
        //recyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.adapter= UserAdapter()
        binding.layoutmanager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        //recyclerView.adapter=UserAdapter()
    }

}