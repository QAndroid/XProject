package workshop1024.com.xproject.utils

import android.content.Context

/**
 * 单位工具，提供了关于单位的转换等方法
 */
object UnitUtils {
    /**
     * dp转px单位
     *
     * @param dpValue dp值
     * @return px值
     */
    @JvmStatic fun dpToPx(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * dp转px单位
     *
     * @param dpValue dp值
     * @return px值
     */
    @JvmStatic fun spToPx(context: Context, spValue: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (spValue * scale + 0.5f).toInt()
    }
}