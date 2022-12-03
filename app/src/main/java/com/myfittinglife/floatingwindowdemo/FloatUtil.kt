package com.myfittinglife.floatingwindowdemo

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * @Author LD
 * @Time 2022/11/17 15:11
 * @Describe
 * @Modify
 */
object FloatUtil {

    const val TAG = "ceshi_FloatUtil"

    //记录以前的X和Y值
    var recordX = -1
    var recordY = -1

    var mFloatingView: FloatingView? = null

    /**
     * 创建和显示悬浮框
     */
    fun createFloatingView(context: Context, isOverlay: Boolean = false): FloatingView {
        val floatingView = FloatingView(context, null)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.apply {
            type = when (isOverlay) {
                true -> {
                    //应用退出仍可显示
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //8.0以上用这个
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    } else {
                        //8.0以下用这个，若都用高版本的，会报错permission denied for window type 2038
                        WindowManager.LayoutParams.TYPE_PHONE
                    }
                }
                false -> {
                    //应用内悬浮框
                    WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG
                }
            }
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            format = PixelFormat.TRANSPARENT
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.LEFT or Gravity.TOP
            //判断是否有记录的位置(用于系统内弹框各个界面的弹框位置一样)
            x = if (recordX == -1) {
                recordX = context.resources.displayMetrics.widthPixels
                context.resources.displayMetrics.widthPixels
            } else {
                recordX
            }
            y = if (recordY == -1) {
                recordY = context.resources.displayMetrics.heightPixels / 2
                context.resources.displayMetrics.heightPixels / 2
                // TODO: 2022/12/3 需要减去状态栏的高度
//                recordY = (context.resources.displayMetrics.heightPixels-getStatusBarHeight(context)) / 2
//                (context.resources.displayMetrics.heightPixels-getStatusBarHeight(context)) / 2
            } else {
                recordY
            }
        }
        floatingView.setMyLayoutParams(layoutParams)
        Log.i("ceshi", "createFloatingWindow: 添加View")
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.addView(floatingView, layoutParams)
        return floatingView
    }

    fun createFloatingView2(context: Context, isOverlay: Boolean = false) {
        if (mFloatingView == null) {
            mFloatingView = FloatingView(context, null)
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.apply {
                type = when (isOverlay) {
                    true -> {
                        //应用退出仍可显示
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            //8.0以上用这个
                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                        } else {
                            //8.0以下用这个，若都用高版本的，会报错permission denied for window type 2038
                            WindowManager.LayoutParams.TYPE_PHONE
                        }
                    }
                    false -> {
                        //应用内悬浮框
                        WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG
                    }
                }
                flags =
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                format = PixelFormat.TRANSPARENT

                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
                gravity = Gravity.LEFT or Gravity.TOP
                //判断是否有记录的位置(用于系统内弹框各个界面的弹框位置一样)
                x = if (recordX == -1) {
                    recordX = context.resources.displayMetrics.widthPixels
                    context.resources.displayMetrics.widthPixels
                } else {
                    recordX
                }
                y = if (recordY == -1) {
                    recordY = context.resources.displayMetrics.heightPixels / 2
                    context.resources.displayMetrics.heightPixels / 2
                } else {
                    recordY
                }
            }
            mFloatingView!!.setMyLayoutParams(layoutParams)
            Log.i("ceshi", "createFloatingWindow: 添加View")
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.addView(mFloatingView, layoutParams)
        }
    }

    /**
     * 移除FloatingView
     */
    fun removeFloatingView(context: Context, floatingView: FloatingView?) {
        if (floatingView != null) {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//            windowManager.removeView(floatingView)
            windowManager.removeViewImmediate(floatingView)
//            floatingView = null
            Log.i(TAG, "removeFloatingView: 移除弹框")
        }
    }

    fun removeFloatingView2(context: Context) {
        mFloatingView?.let {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//            windowManager.removeView(floatingView)
            windowManager.removeViewImmediate(it)
            mFloatingView = null
            Log.i(TAG, "removeFloatingView: 移除弹框")
        }
    }

    /**
     * 更新FloatingView位置
     */
    fun updateFloatingView(floatingView: FloatingView?) {
        Log.i("ceshi", "updateFloatingView: recordX:$recordX recordY:$recordY")
        floatingView?.updateToLocation(recordX, recordY)
    }

    //系统的不需要设置，因为其只有一个view,在FloatingView中的onTouch方法里已经移动了
    fun updateFloatingView2() {
        mFloatingView?.updateToLocation(recordX, recordY)
    }

    fun hide(floatingView: FloatingView?) {
        floatingView?.visibility = View.GONE
    }

    fun show(floatingView: FloatingView?) {
        floatingView?.visibility = View.VISIBLE
    }


    private fun getStatusBarHeight(context: Context): Int {
        var mStatusBarHeight=0
        val resourceId: Int =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            mStatusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        return mStatusBarHeight
    }

}