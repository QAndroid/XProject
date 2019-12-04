package workshop1024.com.xproject.introduce.controller.fragment

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.junit.Test
import org.junit.runner.RunWith
import workshop1024.com.xproject.base.test.matcher.ImageViewMatchers
import workshop1024.com.xproject.introduce.IntroduceFragment
import workshop1024.com.xproject.introduce.R


//Fragment测试：参考：https://developer.android.com/training/basics/fragments/testing
@RunWith(AndroidJUnit4::class)
//@MediumTest，该测试Case只测试Fragmnet内不的业务逻辑，IntroduceActivity是@LargeTest，测试Fragment和Bar继承的Case
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
        onView(withText("Welcome")).check(matches(isDisplayed()))
        onView(ImageViewMatchers.hasSrcDrawable(R.drawable.introduce_icon1)).check(matches(isDisplayed()))
        onView(withText("Thank you for installing Paperboy.What makes Paperboy different form other news reader apps is its simplicity and elegant design.")).check(matches(isDisplayed()))
    }

    @Test
    fun introduce2_show() {
        val fragmentArs = Bundle().apply {
            putInt(IntroduceFragment.LAYOUT_ID, R.layout.introduce2_fragment)
        }
        launchFragmentInContainer<IntroduceFragment>(fragmentArs)

        onView(withText("Customizations")).check(matches(isDisplayed()))
        onView(ImageViewMatchers.hasSrcDrawable(R.drawable.introduce_icon2)).check(matches(isDisplayed()))
        onView(withText("We believe tha each user is unique and hence several customization options are provided to suit different reading styles.To customize please visit the settings page.")).check(matches(isDisplayed()))
    }

    @Test
    fun introduce3_show() {
        val fragmentArs = Bundle().apply {
            putInt(IntroduceFragment.LAYOUT_ID, R.layout.introduce3_fragment)
        }
        launchFragmentInContainer<IntroduceFragment>(fragmentArs)

        onView(withText("Reading pattern learner")).check(matches(isDisplayed()))
        onView(ImageViewMatchers.hasSrcDrawable(R.drawable.introduce_icon3)).check(matches(isDisplayed()))
        onView(withText("The home screen tiles will automatically reaarrange based on reading patterns for better user experence.All data is stored locally kepping in mind user privacy.")).check(matches(isDisplayed()))
    }
}
