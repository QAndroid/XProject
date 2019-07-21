package workshop1024.com.xproject.controller.activity.login

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.activity.introduce.IntroduceActivity
import workshop1024.com.xproject.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //对象的创建不使用new关键字
        //var可重复复制变量
        //语句结尾不使用;
        val binding = DataBindingUtil.setContentView<LoginActivityBinding>(this, R.layout.login_activity)
        binding.loginHandlers = LoginHandlers()
    }

    //内部类，使用inner关键字声明
    inner class LoginHandlers {
        fun onClickLogin(view: View) {
            Toast.makeText(this@LoginActivity, "login Click", Toast.LENGTH_SHORT).show()
        }

        fun onClickTry(view: View) {
            IntroduceActivity.startActivity(this@LoginActivity)
            Toast.makeText(this@LoginActivity, "try Click", Toast.LENGTH_SHORT).show()
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