package workshop1024.com.xproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import workshop1024.com.xproject.R;

/**
 * 登陆选择页面
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private Button mLoginButton;
    private Button mTryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mLoginButton = findViewById(R.id.button_login);
        mTryButton = findViewById(R.id.button_try);
        mLoginButton.setOnClickListener(this);
        mTryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mLoginButton) {
            Toast.makeText(LoginActivity.this, "login click", Toast.LENGTH_SHORT).show();
        } else if (view == mTryButton) {
            Intent intent = new Intent(LoginActivity.this, IntroduceActivity.class);
            startActivity(intent);
        }
    }
}
