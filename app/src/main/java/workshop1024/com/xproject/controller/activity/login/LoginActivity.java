package workshop1024.com.xproject.controller.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.introduce.IntroduceActivity;
import workshop1024.com.xproject.databinding.ActivityLoginBinding;

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
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginHandlers handlers = new LoginHandlers();
        binding.setLoginHandlers(handlers);
    }

    public class LoginHandlers {
        public void onClickLogin() {
            Toast.makeText(LoginActivity.this, "login click", Toast.LENGTH_SHORT).show();
        }

        public void onClickTry() {
            IntroduceActivity.startActivity(LoginActivity.this);
        }
    }
}
