package workshop1024.com.xproject.base.test.matcher

import android.view.View
import androidx.annotation.DrawableRes
import org.hamcrest.Matcher

/**
 * ImageView 自定义匹配器
 */

class ImageViewMatchers {

    companion object {
        fun hasSrcDrawable(@DrawableRes id: Int): Matcher<View> {
            return SrcMatchers(id)
        }
    }
}