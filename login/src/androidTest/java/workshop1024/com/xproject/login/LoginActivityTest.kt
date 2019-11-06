package workshop1024.com.xproject.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import workshop1024.com.xproject.login.controller.activity.LoginActivity

//java.lang.NoClassDefFoundError: Failed resolution of: Landroidx/databinding/DataBinderMapperImpl;
//@RunWith(AndroidJUnit4::class)解决，FIXME 目前不知道原因？
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    //org.junit.internal.runners.rules.ValidationError: The @Rule 'activityTestRule' must be public
    //参考：https://stackoverflow.com/questions/29945087/kotlin-and-new-activitytestrule-the-rule-must-be-public
    @get:Rule
    val activityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun check_TryButtonShow() {
        onView(withId(R.id.login_button_try)).check(matches(isDisplayed()))
    }

    @Test
    fun clickTryButton_toPageIntroduce() {
        onView(withId(R.id.login_button_try)).perform(click())
    }
}