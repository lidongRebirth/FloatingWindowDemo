package com.myfittinglife.floatingwindowdemo.activity.base

import androidx.appcompat.app.AppCompatActivity
import com.myfittinglife.floatingwindowdemo.FloatUtil
import com.myfittinglife.floatingwindowdemo.FloatingView

/**
 * @Author LD
 * @Time 2022/12/2 11:49
 * @Describe 基类Activity(用于系统内全局弹框)
 * @Modify
 */
open class BaseActivity : AppCompatActivity() {

     var floatingView: FloatingView? = null

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

//        FloatUtil.createFloatingView2(this,false)

        if (floatingView == null) {
            floatingView = FloatUtil.createFloatingView(this,false)
        }

        // TODO: 2022/12/2 自己点击创建或者是在该方法内创建
        //不同的是：如果是自己创建则需要延迟创建，在activity创建后再延迟创建这个
    }

    //更新位置
    override fun onResume() {
        super.onResume()
        FloatUtil.updateFloatingView(floatingView)
//        FloatUtil.updateFloatingView2()
    }

    override fun onDestroy() {
        super.onDestroy()
        FloatUtil.removeFloatingView(this, floatingView)
//        FloatUtil.removeFloatingView2(this)
    }

}