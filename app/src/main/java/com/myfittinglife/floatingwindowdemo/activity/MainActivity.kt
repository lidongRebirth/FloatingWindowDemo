package com.myfittinglife.floatingwindowdemo.activity

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.myfittinglife.floatingwindowdemo.FloatingView
import com.myfittinglife.floatingwindowdemo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ceshi_main"
    }

    lateinit var mWindowManager: WindowManager
    private var floatingView: FloatingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //跳到基于BaseActivity的弹框
        btn1.setOnClickListener {
            startActivity(Intent(this,BaseDialogActivity::class.java))
        }
        //跳转到系统弹框
        btn2.setOnClickListener {
            startActivity(Intent(this,SystemDialogActivity::class.java))
        }

        //屏幕高度
        var screenHeight =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                windowManager.currentWindowMetrics.bounds.height()
            } else {
                windowManager.defaultDisplay.height
            }

    }

    /**
     * 创建FloatingWindow
     */
    private fun createFloatingWindow() {


//        //屏幕宽度
//        var screenWidth =
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
//                windowManager.currentWindowMetrics.bounds.width()
////                windowManager.maximumWindowMetrics.bounds.width()
//            } else {
//                windowManager.defaultDisplay.width
//            }
//        //屏幕高度
//        var screenHeight =
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
//                windowManager.currentWindowMetrics.bounds.height()
////                windowManager.maximumWindowMetrics.bounds.height()
//            } else {
//                windowManager.defaultDisplay.height
//            }
//        Log.i(TAG, "createFloatingWindow: screenHeight:$screenHeight screenWidth:$screenWidth")
//        //可以显示大小的绝对高度,和windowManager.defaultDisplay.height获取的高度一致，但比windowManager.maximumWindowMetrics.bounds.height()的值小
//        val mheight = resources.displayMetrics.heightPixels
//        val mwidth = resources.displayMetrics.widthPixels
//        Log.i(TAG, "createFloatingWindow: height:$mheight width:$mwidth")


        floatingView = FloatingView(this, null)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.apply {
            //需要权限(设置了也会闪崩)
//            type = WindowManager.LayoutParams.TYPE_PHONE
            //需要权限(允许应用显示悬浮窗权限授予后可后台显示弹框)
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            //不需要权限，仅在应用内显示
//            type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG


            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//            format = PixelFormat.RGBA_8888
            format = PixelFormat.TRANSPARENT

            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT

//            width = floatingView!!.viewWidth
//            height = floatingView!!.viewHeight
            //先根据Gravity来放置窗口，然后根据x,y属性来移动
            gravity = Gravity.LEFT or Gravity.TOP

            //初始显示的位置（是根据Gravity设置后来偏移相应的位置，而不是设置在哪就显示在哪）
            x = resources.displayMetrics.widthPixels
            y = resources.displayMetrics.heightPixels / 2
        }

//        val layoutParams = WindowManager.LayoutParams(
//            ViewGroup.LayoutParams.WRAP_CONTENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                    or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            PixelFormat.TRANSLUCENT
//        )
//        layoutParams.gravity = Gravity.TOP or Gravity.LEFT
//        //初始显示的位置
//        layoutParams.x = 0
//        layoutParams.y = getResources().getDisplayMetrics().heightPixels / 3 * 2


        floatingView?.let {
            it.setMyLayoutParams(layoutParams)
            Log.i("ceshi", "createFloatingWindow: 添加View")
            windowManager.addView(it, layoutParams)
        }
    }

    private fun removeFloatingWindow() {
        if (floatingView != null) {
            windowManager.removeView(floatingView)
            floatingView = null
        }
    }


//    override fun onResume() {
//        super.onResume()
//        FloatUtil.updateFloatingView(floatingView)
//        FloatUtil.show(floatingView)
//    }
//
//    override fun onPause() {
//        super.onPause()
////        com.myfittinglife.floatingwindowdemo.FloatUtil.hide(floatingView)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        FloatUtil.removeFloatingView(this, floatingView)
//    }
}