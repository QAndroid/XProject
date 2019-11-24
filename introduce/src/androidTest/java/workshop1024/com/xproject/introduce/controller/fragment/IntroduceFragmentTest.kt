package workshop1024.com.xproject.introduce.controller.fragment

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import workshop1024.com.xproject.base.test.matcher.ImageViewMatchers
import workshop1024.com.xproject.introduce.R


//Fragment测试：参考：https://developer.android.com/training/basics/fragments/testing
@RunWith(AndroidJUnit4::class)
@MediumTest
class IntroduceFragmentTest {

    @Test
    fun introduce1_show() {
        //bundle传入布局introduce1_fragment，启动IntroduceFragment
        val fragmentArs = Bundle().apply {
            putInt(IntroduceFragment.LAYOUT_ID, R.layout.introduce1_fragment)
        }
        launchFragmentInContainer<IntroduceFragment>(fragmentArs)

        //验证标题，图标和说明是否展示
        onView(withText(R.string.introduce1_title)).check(matches(isDisplayed()))
        onView(ImageViewMatchers.hasSrcDrawable(R.drawable.introduce_icon1)).check(matches(isDisplayed()))
        onView(withText(R.string.introduce1_description)).check(matches(isDisplayed()))
    }

    @Test
    fun introduce2_show() {
        val fragmentArs = Bundle().apply {
            putInt(IntroduceFragment.LAYOUT_ID, R.layout.introduce2_fragment)
        }
        launchFragmentInContainer<IntroduceFragment>(fragmentArs)

        onView(withText(R.string.introduce2_title)).check(matches(isDisplayed()))
        onView(ImageViewMatchers.hasSrcDrawable(R.drawable.introduce_icon2)).check(matches(isDisplayed()))
        onView(withText(R.string.introduce2_description)).check(matches(isDisplayed()))
    }

    @Test
    fun introduce3_show() {
        val fragmentArs = Bundle().apply {
            putInt(IntroduceFragment.LAYOUT_ID, R.layout.introduce3_fragment)
        }
        launchFragmentInContainer<IntroduceFragment>(fragmentArs)

        onView(withText(R.string.introduce3_title)).check(matches(isDisplayed()))
        onView(ImageViewMatchers.hasSrcDrawable(R.drawable.introduce_icon3)).check(matches(isDisplayed()))
        onView(withText(R.string.introduce3_description)).check(matches(isDisplayed()))
    }
}
