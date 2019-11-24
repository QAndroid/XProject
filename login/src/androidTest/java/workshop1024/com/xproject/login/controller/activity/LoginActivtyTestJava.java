package workshop1024.com.xproject.login.controller.activity;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import workshop1024.com.xproject.login.R;
import workshop1024.com.xproject.login.controller.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.hasTextColor;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

//FIXME 使用Java语言，就可以分类和方法运行Test，原因目前未知！！
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivtyTestJava {
    public static final String ACTION_INTRODUCE_ACTIVITY = "workshop1024.com.xproject.introduce.controller.activity.IntroduceActivity";

    @Rule
    public IntentsTestRule<LoginActivity> mIntentsRule = new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void tryButton_Show2() {
        ViewInteraction tryButton = onView(withId(R.id.login_button_try));
        tryButton.check(matches(isDisplayed()));
        tryButton.check(matches(withText("TRY THINGS OUT")));
        //FIXME check的资源引用主功能的资源，在主工程只改资源内容，不改资源名称情况下无法check错误？
        tryButton.check(matches(hasTextColor(R.color.white)));
        tryButton.check(matches(ViewMatchers.Companion.hasBackgroundColor(R.color.login_try_background)));
    }

    @Test
    public void loginButton_Show3() {
        ViewInteraction loginButton = onView(withId(R.id.login_button_login));
        loginButton.check(matches(isDisplayed()));
        loginButton.check(matches(withText("Hello from C++")));
        loginButton.check(matches(hasTextColor(R.color.white)));
        loginButton.check(matches(ViewMatchers.Companion.hasBackgroundColor(R.color.login_login_background)));
    }


    @Test
    public void tryButton_Click1() {
        onView(withId(R.id.login_button_try)).perform(click());
        intended(allOf(hasAction(ACTION_INTRODUCE_ACTIVITY)));
    }
}

