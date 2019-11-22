package workshop1024.com.xproject.login.controller.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import workshop1024.com.xproject.base.utils.IntentUtils
import workshop1024.com.xproject.login.R
import workshop1024.com.xproject.login.controller.native.NativeLib
import workshop1024.com.xproject.login.databinding.LoginActivityBinding

//ARouter：在支持路由的页面上添加注解(必选) ，这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/login/LoginActivity")
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //对象的创建不使用new关键字
        //var可重复复制变量
        //语句结尾不使用;
        val binding = DataBindingUtil.setContentView<LoginActivityBinding>(this, R.layout.login_activity)
        binding.loginHandlers = LoginHandlers()

        binding.loginButtonLogin.text = NativeLib().stringFromJNI()
    }

    //内部类，使用inner关键字声明
    inner class LoginHandlers {
        fun onClickLogin(view: View) {
            Toast.makeText(this@LoginActivity, "login Click", Toast.LENGTH_SHORT).show()
        }

        fun onClickTry(view: View) {
            //使用隐式意图实现（在AndroidManifest.xml中声明的intent-filter），组件之间的跳转，避免组件间的依赖
            Intent().apply {
                action = "workshop1024.com.xproject.introduce.controller.activity.IntroduceActivity"
                IntentUtils.startActivityByIntent(this@LoginActivity, this)
            }
        }
    }

    //伴生对象类的唯一对象，声明的方法和成员都是类的唯一值
    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}