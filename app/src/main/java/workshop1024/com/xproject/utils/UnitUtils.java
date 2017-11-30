package workshop1024.com.xproject.utils;

import android.content.Context;

/**
 * 单位工具，提供了关于单位的转换等方法
 */
public class UnitUtils {
    /**
     * dp转px单位
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
