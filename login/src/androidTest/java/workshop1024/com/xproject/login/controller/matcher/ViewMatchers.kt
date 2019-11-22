package workshop1024.com.xproject.login.controller.matcher

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import androidx.test.internal.util.Checks
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import androidx.annotation.DrawableRes

/**
 * View（ViewGroup）自定义匹配器
 */
class ViewMatchers {

    companion object {
        //自定义View/ViewGroup背景颜色匹配器
        //参考：创建一个自定义 Espresso matcher，https://juejin.im/entry/5794c121128fe10056b84781
        fun hasBackgroundColor(colorRes: Int): Matcher<View> {
            Checks.checkNotNull(colorRes)

            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description?) {
                    description?.appendText("background color: $colorRes")
                }

                override fun matchesSafely(item: View?): Boolean {

                    val context = item?.context
                    val textViewColor = (item?.background as ColorDrawable).color
                    val expectedColor: Int?

                    if (Build.VERSION.SDK_INT <= 22) {
                        expectedColor = context?.resources?.getColor(colorRes, null)
                    } else {
                        expectedColor = context?.getColor(colorRes)
                    }

                    return textViewColor == expectedColor
                }
            }
        }

        fun hasBackgroundDrawable(@DrawableRes id: Int): Matcher<View> {
            return BackgroundMatcher(id)
        }
    }
}