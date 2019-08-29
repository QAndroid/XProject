package workshop1024.com.xproject.controller.activity.login

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import workshop1024.com.xproject.R
import workshop1024.com.xproject.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //对象的创建不使用new关键字
        //var可重复复制变量
        //语句结尾不使用;
        val binding = DataBindingUtil.setContentView<LoginActivityBinding>(this, R.layout.login_activity)
        binding.loginHandlers = LoginHandlers()

        binding.loginButtonLogin.text = stringFromJNI()
    }

    //内部类，使用inner关键字声明
    inner class LoginHandlers {
        fun onClickLogin(view: View) {
            Toast.makeText(this@LoginActivity, "login Click", Toast.LENGTH_SHORT).show()
        }

        fun onClickTry(view: View) {
            //使用隐式意图时间，组件之间的跳转，避免组件间的依赖
            //常规的隐式意图
            val intent = Intent("workshop1024.com.xproject.introduce.controller.activity.IntroduceActivity")
            //隐式意图需要验证至少有一个应用能够处理该Intent，才可以安全调用startActivity
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
                Toast.makeText(this@LoginActivity, "try Click", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    //伴生对象类的唯一对象，声明的方法和成员都是类的唯一值
    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}