package com.myfittinglife.floatingwindowdemo.activity

import android.content.Intent
import android.os.Bundle
import com.myfittinglife.floatingwindowdemo.R
import com.myfittinglife.floatingwindowdemo.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base_dialog.*

/**
 @Author LD
 @Time 2022/12/2 11:55
 @Describe 基于BaseActivity来实现全局的Dialog
 @Modify
*/
class BaseDialogActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_dialog)

        btnJump.setOnClickListener {
            startActivity(Intent(this,Activity2::class.java))
        }
    }
}