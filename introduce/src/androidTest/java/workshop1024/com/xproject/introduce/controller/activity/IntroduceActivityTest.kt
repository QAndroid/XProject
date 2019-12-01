package workshop1024.com.xproject.introduce.controller.activity

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import workshop1024.com.xproject.base.test.matcher.ImageViewMatchers
import workshop1024.com.xproject.introduce.R

//IntroduceActivityTest，负责ViewPager Fragment + 底部栏集成的交互case
@RunWith(AndroidJUnit4::class)
@LargeTest
class IntroduceActivityTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(IntroduceActivity::class.java)

    @Test
    fun page1_Show() {
        //启动介绍页面，默认在第一页
        //检查第一页中ViewPager Fragment中的展示
        onView(withText("Welcome")).check(matches(isDisplayed()))
        //TODO 后期查找原因
        onView(ImageViewMatchers.hasSrcDrawable(R.drawable.introduce_icon1)).check(matches(isDisplayed()))
        onView(withText("Thank you for installing Paperboy.What makes Paperboy different form other news reader apps is its simplicity and elegant design.")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //检查底部状态栏展示
        onView(withId(R.id.introduce_button_skip)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("SKIP")))
        }
        onView(withId(R.id.introduce_button_next)).apply {
            check(matches(isDisplayed()))
        }
        onView(withId(R.id.introduce_button_done)).check(matches(not(isDisplayed())))
    }

    @Test
    fun page2_Show() {
        //ViewPager右滑动，滑动到第二页
        onView(withId(R.id.introduce_viewpager_content)).perform(swipeLeft())


        onView(withText("Customizations")).check(matches(isDisplayed()))
//        onView(ImageViewMatchers.hasSrcDrawable(R.drawable.introduce_icon2)).check(matches(isDisplayed()))
        onView(withText("We believe tha each user is unique and hence several customization options are provided to suit different reading styles.To customize please visit the settings page.")).check(matches(isDisplayed()))

        //检查底部状态栏展示
        onView(withId(R.id.introduce_button_skip)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("SKIP")))
        }
        onView(withId(R.id.introduce_button_next)).apply {
            check(matches(isDisplayed()))
        }
        onView(withId(R.id.introduce_button_done)).check(matches(not(isDisplayed())))
    }

    @Test
    fun page3_Show() {
        //点击Next按钮，滑动到第三页
        //FIXME ViewPagerActions如何使用？
        onView(withId(R.id.introduce_button_next)).perform(click()).perform(click())

        onView(withText("Reading pattern learner")).check(matches(isDisplayed()))
//        onView(ImageViewMatchers.hasSrcDrawable(R.drawable.introduce_icon3)).check(matches(isDisplayed()))
        onView(withText("The home screen tiles will automatically reaarrange based on reading patterns for better user experence.All data is stored locally kepping in mind user privacy.")).check(matches(isDisplayed()))

        //检查底部状态栏展示
        onView(withId(R.id.introduce_button_skip)).check(matches(not(isDisplayed())))
        onView(withId(R.id.introduce_button_next)).check(matches(not(isDisplayed())))
        //TODO 中间的指示器如何测试？？？
        onView(withId(R.id.introduce_button_done)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("DONE")))
        }
    }
}