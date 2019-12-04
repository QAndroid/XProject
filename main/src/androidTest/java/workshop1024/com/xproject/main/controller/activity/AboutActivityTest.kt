package workshop1024.com.xproject.main.controller.activity

import android.widget.ImageButton
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.*
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import workshop1024.com.xproject.main.R
import workshop1024.com.xproject.main.about.AboutActivity

@RunWith(AndroidJUnit4::class)
class AboutActivityTest {
    @get:Rule
    val intentsTestRule = IntentsTestRule(AboutActivity::class.java)

    @Test
    fun checkPageShow() {
        //Toolbar的Title展示
        //参考：https://stackoverflow.com/questions/36329978/how-to-check-toolbar-title-in-android-instrumental-test
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.about_toolbar_navigator)))).check(matches(withText("About Paperboy")))
        onView(withText("What does Paperboy do?")).apply {
            check(matches(isDisplayed()))
            //FIXME，该check依赖于main模块下的颜色资源
            check(matches(hasTextColor(R.color.xproject_daynight_textcolor)))
        }
        onView(withText("Paperboy makes reading stories a beautiful and immersive experience.Simplicity and consistency is the core to the design making reading enjoyable.")).apply {
            check(matches(isDisplayed()))
            check(matches(hasTextColor(R.color.xproject_daynight_textcolor)))
        }
        onView(withText("How can you get involved?")).apply {
            check(matches(isDisplayed()))
            check(matches(hasTextColor(R.color.xproject_daynight_textcolor)))
        }
        onView(withText("We provide early access to the latest and greatest features of Paperboy to the Google Plus alpha testing community.Come and join us there.")).apply {
            check(matches(isDisplayed()))
            check(matches(hasTextColor(R.color.xproject_daynight_textcolor)))
        }
        onView(withText("How can we find you?")).apply {
            check(matches(isDisplayed()))
            check(matches(hasTextColor(R.color.xproject_daynight_textcolor)))
        }
        onView(withText("Do like us on Facebook,twitter and on our Google plus channel to get the latest features on Paperboy.")).apply {
            check(matches(isDisplayed()))
            check(matches(hasTextColor(R.color.xproject_daynight_textcolor)))
        }
    }

    @Test
    fun clickBack_CheckBack() {
        onView(allOf(instanceOf(ImageButton::class.java), withParent(withId(R.id.about_toolbar_navigator)))).perform(click())
        //检测当前Activity是否关闭
        //参考：https://stackoverflow.com/questions/35863134/espresso-how-to-test-if-activity-is-finished
        assertTrue(intentsTestRule.activity.isFinishing)
    }
}