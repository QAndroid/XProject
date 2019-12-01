package workshop1024.com.xproject.login.controller.activity

import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.core.StringContains.containsString
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import workshop1024.com.xproject.base.test.matcher.ImageViewMatchers
import workshop1024.com.xproject.login.R
import workshop1024.com.xproject.login.controller.matcher.ViewMatchers

//java.lang.NoClassDefFoundError: Failed resolution of: Landroidx/databinding/DataBinderMapperImpl;
//@RunWith(AndroidJUnit4::class)解决，FIXME 目前不知道原因？
@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {

    //org.junit.internal.runners.rules.ValidationError: The @Rule 'activityTestRule' must be public
    //JUnit允许通过一个测试类或者一个getter方法提供rule。如果你在Kotlin中使用属性，JUnit无法识别
    //参考：https://stackoverflow.com/questions/29945087/kotlin-and-new-activitytestrule-the-rule-must-be-public
    @get:Rule
    val intentsTestRule = IntentsTestRule(LoginActivity::class.java)

    @Test
    fun checkPageShow() {
        //检测页面的背景颜色
        onView(withId(R.id.login_linearlayout_root)).apply {
            check(matches(isDisplayed()))
            check(matches(ViewMatchers.hasBackgroundDrawable(R.drawable.login_background)))
        }
        //这里因为页面上只有icon一个ImageView，所以才采用withClassName
        onView(withClassName(containsString(ImageView::class.simpleName))).apply {
            check(matches(isDisplayed()))
            check(matches(ImageViewMatchers.hasSrcDrawable(R.drawable.login_icon)))
        }
        onView(withId(R.id.login_button_try)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("TRY THINGS OUT")))
            check(matches(hasTextColor(R.color.white)))
            check(matches(ViewMatchers.hasBackgroundColor(R.color.login_try_background)))
        }
        onView(withId(R.id.login_button_login)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("Hello from C++")))
            check(matches(hasTextColor(R.color.white)))
            check(matches(ViewMatchers.hasBackgroundColor(R.color.login_login_background)))
        }
    }

    @Test
    fun tryButton_Click() {
        onView(withId(R.id.login_button_try)).perform(click())
        //FIXME 点击以后起来的base IntentUtils无法验证通过，无法发出Intent验证？？
        //此种情况应该如何验证？？
        assertFalse(intentsTestRule.activity.isFinishing)
    }
}