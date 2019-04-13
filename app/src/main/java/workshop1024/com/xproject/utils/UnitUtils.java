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
    public static int dpToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * dp转px单位
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int spToPx(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }
}
