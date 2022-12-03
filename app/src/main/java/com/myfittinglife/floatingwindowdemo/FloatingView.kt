package com.myfittinglife.floatingwindowdemo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.LinearLayout

/**
 * @Author LD
 * @Time 2022/11/16 10:58
 * @Describe
 * @Modify
 */
class FloatingView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    companion object {
        const val TAG = "ceshi_FloatingView"
    }

    private var statusBarHeight = 0

    //View相对于屏幕的位置(包含状态栏)
    var xInScreen: Float = 0f
    var yInScreen: Float = 0f


    //手指按下时相对于屏幕的坐标
    var xDownInScreen: Float = 0f
    var yDownInScreen: Float = 0f

    // 手指按下时相对于该悬浮View的坐标
    var xInView: Float = 0f
    var yInView: Float = 0f


    var layoutParams: WindowManager.LayoutParams? = null
    private var windowManager: WindowManager? = null


    init {


        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        //加载布局
        val view = LayoutInflater.from(context).inflate(R.layout.item_floating, this)

    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            //手指按下
            //记录按下的横纵坐标(纵坐标需要减去状态栏高度)
            MotionEvent.ACTION_DOWN -> {

                xInView = event.x
                yInView = event.y
                //使用此会在移动时首先先跳转到坐标点后才能移动

                //-----相对于屏幕的位置
                xInScreen=event.rawX
                yInScreen=event.rawY

                //手指按下的坐标(用来判断点击事件)
                xDownInScreen = event.rawX
//                yDownInScreen = event.rawY - getMyStatusBarHeight()
                yDownInScreen = event.rawY

                return true

            }
            //手指移动
            //手指移动的时候更新小悬浮窗的位置
            MotionEvent.ACTION_MOVE -> {
                xInScreen = event.rawX
                //不减去状态栏的高度，会在刚开始移动时偏移一段
                yInScreen = event.rawY- getMyStatusBarHeight()



                updatePosition()
                return true
            }
            //手指抬起
            //当手指抬起的位置和按下的坐标相同，则代表点击事件
            MotionEvent.ACTION_UP -> {

                //弹起的坐标(用来判断点击事件)
                val xUpInScreen = event.rawX
//                val yUpInScreen = event.rawY-getMyStatusBarHeight()
                val yUpInScreen = event.rawY



                if (xDownInScreen == xUpInScreen && yDownInScreen == yUpInScreen) {
                    //点击事件
                    clickFun()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }


    fun setMyLayoutParams(params: WindowManager.LayoutParams) {
        layoutParams = params
    }

    fun getMyLayoutParams(): WindowManager.LayoutParams? {
        return layoutParams
    }


    /**
     * 点击事件
     */
    private fun clickFun() {
        Log.i(TAG, "clickFun: 点击事件")
    }

    /**
     * 更新悬浮框的位置
     */
    private fun updatePosition() {

        layoutParams?.let {

            it.x = (xInScreen - xInView).toInt()
            it.y= (yInScreen-yInView).toInt()

            //记录更新的位置(用于基于应用的悬浮框来定位上个页面的位置)
            FloatUtil.recordX=(xInScreen - xInView).toInt()
            FloatUtil.recordY=(yInScreen-yInView).toInt()
            windowManager?.updateViewLayout(this, it)
        }
    }

    fun updateToLocation(x: Int, y: Int) {
        layoutParams?.let {
            it.x = x
            it.y = y
            windowManager?.updateViewLayout(this, it)
        }
    }


    // TODO: 2022/11/16 研究是否可用
    private fun getMyStatusBarHeight(): Int {
        if (statusBarHeight == 0) {
            try {
                val c = Class.forName("com.android.internal.R\$dimen")
                val o = c.newInstance()
                val field = c.getField("status_bar_height")
                val x = field[o] as Int
                statusBarHeight =
                    resources.getDimensionPixelSize(x)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return statusBarHeight
    }


}