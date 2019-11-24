package workshop1024.com.xproject.login.controller.activity

import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.core.StringContains.containsString
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

    companion object {
        const val ACTION_INTRODUCE_ACTIVITY = "workshop1024.com.xproject.introduce.controller.activity.IntroduceActivity"
    }

    //org.junit.internal.runners.rules.ValidationError: The @Rule 'activityTestRule' must be public
    //参考：https://stackoverflow.com/questions/29945087/kotlin-and-new-activitytestrule-the-rule-must-be-public
    @get:Rule
    val intentsTestRule = IntentsTestRule(LoginActivity::class.java)

    @Test
    fun loginPage_show() {
        //检测页面的背景颜色
        onView(withId(R.id.login_linearlayout_root)).apply {
            check(matches(isDisplayed()))
            check(matches(ViewMatchers.hasBackgroundDrawable(R.drawable.login_background)))
        }
    }

    @Test
    fun iconImageView_Show() {
        //这里因为页面上只有icon一个ImageView，所以才采用withClassName
        onView(withClassName(containsString(ImageView::class.simpleName))).apply {
            check(matches(isDisplayed()))
            check(matches(ImageViewMatchers.hasSrcDrawable(R.drawable.login_icon)))
        }
    }

    @Test
    fun tryButton_Show() {
        onView(withId(R.id.login_button_try)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("TRY THINGS OUT")))
            check(matches(hasTextColor(R.color.white)))
            check(matches(ViewMatchers.hasBackgroundColor(R.color.login_try_background)))
        }
    }

    @Test
    fun loginButton_Show() {
        onView(withId(R.id.login_button_login)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("Hello from C++")))
            check(matches(hasTextColor(R.color.white)))
            check(matches(ViewMatchers.hasBackgroundColor(R.color.login_login_background)))
        }
    }

    @Test
    fun tryButton_Click() {
        //FIXME 该Case发送Intent之前做了是否有匹配校验，所以跨组件Intent无法发出后校验
//        onView(withId(R.id.login_button_try)).perform(click())
//        intended(allOf(hasAction(ACTION_INTRODUCE_ACTIVITY)))
    }
}