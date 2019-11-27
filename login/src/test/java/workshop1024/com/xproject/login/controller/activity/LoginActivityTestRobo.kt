package workshop1024.com.xproject.login.controller.activity

import android.widget.Button
import androidx.viewpager.widget.ViewPager
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import workshop1024.com.xproject.login.R
import kotlin.math.log

@RunWith(RobolectricTestRunner::class)
class LoginActivityTestRobo {
    @Test
    fun page1_Show() {
        //通过Robolectric获取将要测试的activity
        val activity = Robolectric.setupActivity(LoginActivity::class.java)
        //获取该页面的login_button_login按钮
        val loginButton = activity.findViewById<Button>(R.id.login_button_login)
        //断言按钮的展示情况
        assertThat(loginButton.text.toString(), equalTo("LOGIN TO FEEDLY"))
    }
}