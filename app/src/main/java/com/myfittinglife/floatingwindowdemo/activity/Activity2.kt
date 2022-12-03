package com.myfittinglife.floatingwindowdemo.activity

import android.content.Intent
import android.os.Bundle
import com.myfittinglife.floatingwindowdemo.FloatUtil
import com.myfittinglife.floatingwindowdemo.FloatingView
import com.myfittinglife.floatingwindowdemo.R
import com.myfittinglife.floatingwindowdemo.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_2.*

class Activity2 : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        btnJump.setOnClickListener {
            startActivity(Intent(this, Activity3::class.java))
        }

        btnClose.setOnClickListener {
            FloatUtil.removeFloatingView(this,floatingView)
            //一定要赋值为null，不然base里的onResume会更新位置,而原先的floatingView已经不存在导致错误
            floatingView=null
        }

    }
}