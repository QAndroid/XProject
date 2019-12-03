package workshop1024.com.xproject.login.controller.activity

import android.widget.Button
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import workshop1024.com.xproject.login.LoginActivity
import workshop1024.com.xproject.login.R

//FIXME 临时使用回退LoginActivit运行，不支持Databinding和NDK
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