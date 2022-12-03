package com.myfittinglife.floatingwindowdemo.activity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myfittinglife.floatingwindowdemo.FloatUtil
import com.myfittinglife.floatingwindowdemo.FloatingView
import com.myfittinglife.floatingwindowdemo.R
import kotlinx.android.synthetic.main.activity_system_dialog.*


/**
@Author LD
@Time 2022/12/2 15:26
@Describe 系统级的悬浮弹框，应用退出后台仍可展示
@Modify
 */
class SystemDialogActivity : AppCompatActivity() {

    private var floatingView: FloatingView? = null
    private val OVERLAY_PERMISSION_REQ_CODE = 101

    companion object {
        const val TAG = "ceshi_system"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_dialog)

        //判断是否有权限
        btnIsHasPermission.setOnClickListener {
            //android 6.0以上
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                //判断是否有权限
                if (Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "已申请权限", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "未申请权限", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //申请权限
        btnRequestPermission.setOnClickListener {
            requestPermission()
//            isRunningForegroundToApp(this,SystemDialogActivity::class.java)
        }
        //创建弹框
        btnCreate.setOnClickListener {

            //android 6.0以上
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                //判断是否有权限
                if (Settings.canDrawOverlays(this)) {
                    FloatUtil.createFloatingView2(this,true)
//                    if (floatingView == null) {
//                        floatingView=FloatUtil.createFloatingView(this, true)
//                    }
                }else{
                    requestPermission()
                }
            }
        }
        //关闭弹框
        btnClose.setOnClickListener {
//            FloatUtil.removeFloatingView(this, floatingView)
//            floatingView=null
            FloatUtil.removeFloatingView2(this)
            Toast.makeText(this,"移除弹框",Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 申请权限
     */
    private fun requestPermission(){
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            //android R之前可直接跳转到指定的应用
            Uri.parse("package:${packageName}")
        )
        startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE)
    }

    /**
     * 申请后台弹出界面权限
     */
//    fun requestPermission2(){
//        val list = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getRunningTasks(1)
//        if (list != null && list.size > 0) {
//            val cpn = list[0].topActivity
//            Log.e("className", "" + cpn!!.className)
//            if (className.equals(cpn!!.className)) {
//
//            }
//        }
//        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val taskInfoList: List<ActivityManager.RunningTaskInfo> = activityManager.getRunningTasks(20)
//    }
    private fun isRunningForegroundToApp(context: Context, Class: Class<*>) {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        //利用系统方法获取当前Task堆栈, 数目可按实际情况来规划，这里只是演示
        val taskInfoList = activityManager.getRunningTasks(20)
        for (taskInfo in taskInfoList) {
            //遍历找到本应用的 task，并将它切换到前台
            if (taskInfo.baseActivity!!.packageName == context.packageName) {
                Log.d(TAG, "timerTask  pid " + taskInfo.id)
                Log.d(
                    TAG,
                    "timerTask  processName " + taskInfo.topActivity!!.packageName
                )
                Log.d(TAG, "timerTask  getPackageName " + context.packageName)
                activityManager.moveTaskToFront(taskInfo.id, ActivityManager.MOVE_TASK_WITH_HOME)
                val intent = Intent(context, Class)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                context.startActivity(intent)
                break
            }
        }
    }


}