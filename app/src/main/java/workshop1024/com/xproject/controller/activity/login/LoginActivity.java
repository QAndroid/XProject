package workshop1024.com.xproject.controller.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.introduce.IntroduceActivity;
import workshop1024.com.xproject.databinding.LoginActivityBinding;

/**
 * 登陆选择页面
 */
public class LoginActivity extends Activity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        LoginHandlers handlers = new LoginHandlers();
        binding.setLoginHandlers(handlers);
    }

    public class LoginHandlers {
        /**
         * 登录按钮点击
         *
         * @param view
         */
        public void onClickLogin(View view) {
            Toast.makeText(LoginActivity.this, "login click", Toast.LENGTH_SHORT).show();
        }

        /**
         * 尝试按钮点击
         * @param view
         */
        public void onClickTry(View view) {
            IntroduceActivity.startActivity(LoginActivity.this);
        }
    }
}
