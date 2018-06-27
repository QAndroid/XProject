package workshop1024.com.xproject.controller.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.introduce.IntroduceActivity;

/**
 * 登陆选择页面
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    //登陆按钮
    private Button mLoginButton;
    //试用按钮
    private Button mTryButton;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mLoginButton = findViewById(R.id.login_button_login);
        mTryButton = findViewById(R.id.login_button_try);

        mLoginButton.setOnClickListener(this);
        mTryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mLoginButton) {
            Toast.makeText(LoginActivity.this, "login click", Toast.LENGTH_SHORT).show();
        } else if (view == mTryButton) {
            IntroduceActivity.startActivity(this);
        }
    }
}
