package workshop1024.com.xproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.LoginActivityBinding;

/**
 * 登陆选择页面
 */
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.login_activity);

        Handlers handlers = new Handlers();
        binding.setHandlers(handlers);
    }

    //数据绑定-事件绑定-方法引用
    public class Handlers {
        public void onClickLogin(View view) {
            Toast.makeText(LoginActivity.this, "login click", Toast.LENGTH_SHORT).show();
        }

        public void onClickTry(View view) {
            Intent intent = new Intent(LoginActivity.this, IntroduceActivity.class);
            startActivity(intent);
        }
    }
}
