package workshop1024.com.xproject.login

import android.app.Activity
import android.app.Instrumentation
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.isInternal
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.truth.content.IntentSubject.assertThat
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.core.StringContains.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import workshop1024.com.xproject.login.controller.activity.LoginActivity
import workshop1024.com.xproject.login.matcher.CustomMatchers

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
    fun iconImageView_Show() {
        //这里因为页面上只有icon一个ImageView，所以才采用withClassName
        onView(withClassName(containsString(ImageView::class.simpleName))).apply {
            check(matches(isDisplayed()))
            check(matches(CustomMatchers.withDrawableId(R.drawable.login_icon)))
        }
    }

    @Test
    fun tryButton_Show() {
        onView(withId(R.id.login_button_try)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(R.string.login_try)))
            check(matches(hasTextColor(R.color.white)))
            check(matches(CustomMatchers.hasBackgroundColor(R.color.login_try_background)))
        }
    }

    @Test
    fun loginButton_Show() {
        onView(withId(R.id.login_button_login)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("Hello from C++")))
            check(matches(hasTextColor(R.color.white)))
            check(matches(CustomMatchers.hasBackgroundColor(R.color.login_login_background)))
        }
    }

    @Test
    fun tryButton_Click() {
        //FIXME 该Case发送Intent之前做了是否有匹配校验，所以跨组件Intent无法发出后校验
//        onView(withId(R.id.login_button_try)).perform(click())
//        intended(allOf(hasAction(ACTION_INTRODUCE_ACTIVITY)))
    }
}