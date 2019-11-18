package workshop1024.com.xproject.login

import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.core.StringContains.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import workshop1024.com.xproject.login.controller.activity.LoginActivity
import workshop1024.com.xproject.login.matcher.CustomMatchers

//java.lang.NoClassDefFoundError: Failed resolution of: Landroidx/databinding/DataBinderMapperImpl;
//@RunWith(AndroidJUnit4::class)解决，FIXME 目前不知道原因？
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    //org.junit.internal.runners.rules.ValidationError: The @Rule 'activityTestRule' must be public
    //参考：https://stackoverflow.com/questions/29945087/kotlin-and-new-activitytestrule-the-rule-must-be-public
    @get:Rule
    val activityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun check_IconImageViewShow() {
        //这里因为页面上只有icon一个ImageView，所以才采用withClassName
        onView(withClassName(containsString(ImageView::class.simpleName))).apply {
            check(matches(isDisplayed()))
            check(matches(CustomMatchers.withDrawableId(R.drawable.login_icon)))
        }
    }

    @Test
    fun check_TryButtonShow() {
        onView(withId(R.id.login_button_try)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(R.string.login_try)))
            check(matches(hasTextColor(R.color.white)))
            check(matches(CustomMatchers.hasBackgroundColor(R.color.login_try_background)))
        }
    }

    @Test
    fun check_LoginButtonShow() {
        onView(withId(R.id.login_button_login)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("Hello from C++")))
            check(matches(hasTextColor(R.color.white)))
            check(matches(CustomMatchers.hasBackgroundColor(R.color.login_login_background)))
        }
    }
}