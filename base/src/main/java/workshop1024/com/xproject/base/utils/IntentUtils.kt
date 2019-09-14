package workshop1024.com.xproject.base.utils

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import workshop1024.com.xproject.base.exception.IntentNotFoundException

/**
 * 意图相关工具类
 */
object IntentUtils {

    /**
     * 使用意图跳转Activity，包装了意图检测逻辑
     * @param context
     * @param intent 跳转目标页面的Intent
     */
    @Throws(IntentNotFoundException::class)
    fun startActivityByIntent(context: Context, intent: Intent) {
        //检验意图，安全调用
        //FIXME resolveActivity移除模块后，隐式意图跳转崩溃？如何处理不崩溃？
        try {
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
        } catch (exception: ActivityNotFoundException) {
            Log.e("XProject", "跳转的意图${intent}，在app中无法解析");
            throw IntentNotFoundException("跳转的意图${intent}，在app中无法解析", exception)
        }
    }

    /**
     * 使用隐式意图跳转Activity方式1，通过new Intent(activityPath)方式
     * 避免组件间的直接依赖
     * @param context 当前页面上下文
     * @param activityPath 跳转目标页面Activity的类路径
     */
    @Throws(IntentNotFoundException::class)
    fun startActivityByNewIntent(context: Context, activityPath: String) {
        val intent = Intent(activityPath)
        startActivityByIntent(context, intent)
    }

    /**
     * 使用隐式意图跳转Activity方式2，通过指定Intent.setClassName(context,activityPath)方式
     * 避免组件间的直接依赖
     * @param context 当前页面上下文
     * @param activityPath  跳转目标页面Activity的类路径
     */
    @Throws(IntentNotFoundException::class)
    fun startActivityBySetClassName(context: Context, activityPath: String) {
        val intent = Intent()
        intent.setClassName(context, activityPath)
        startActivityByIntent(context, intent)
    }

    /**
     * 使用隐式意图跳转Activity方式2，通过指定Intent.setComponent(compontentName(context,activityPath)方式
     * 避免组件间的直接依赖
     * @param context 当前页面上下文
     * @param activityPath  跳转目标页面Activity的类路径
     */
    @Throws(IntentNotFoundException::class)
    fun startActivityBySetComponent(context: Context, activityPath: String) {
        val intent = Intent()
        intent.setComponent(ComponentName(context.packageName, activityPath))
        startActivityByIntent(context, intent)
    }
}