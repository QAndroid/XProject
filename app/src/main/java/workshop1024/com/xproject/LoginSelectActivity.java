package workshop1024.com.xproject;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import workshop1024.com.xproject.databinding.LoginselectActivityBinding;

/**
 * 登陆选择页面
 */
public class LoginSelectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginselectActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.loginselect_activity);
        LoginSelectHandlers handlers = new LoginSelectHandlers();
        binding.setHandlers(handlers);
    }

    public class LoginSelectHandlers {
        public void onClickLogin(View view) {
            Toast.makeText(LoginSelectActivity.this, "login click", Toast.LENGTH_SHORT).show();
        }

        public void onClickTry(View view) {
            Toast.makeText(LoginSelectActivity.this, "try click", Toast.LENGTH_SHORT).show();
        }
    }
}
