package workshop1024.com.xproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.fragment.HomeFragment;
import workshop1024.com.xproject.fragment.SavedFragment;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View
        .OnClickListener {
    private DrawerLayout mDrawerLayut;
    private NavigationView mNavigationView;
    private Button mLoginButton;
    private ImageButton mLogoutButton;
    private Toolbar mToolbar;
    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private int mSelectItemId = R.id.leftnavigator_menu_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mDrawerLayut = findViewById(R.id.drawerlayout_navigator);
        mNavigationView = findViewById(R.id.navigationview_left);
        View headerLayout = mNavigationView.inflateHeaderView(R.layout.main_leftnavigator_header);
        mLoginButton = headerLayout.findViewById(R.id.leftnavigator_button_login);
        mLogoutButton = headerLayout.findViewById(R.id.leftnavigator_imagebutton_logout);
        mToolbar = findViewById(R.id.toolbar_navigator);

        mLoginButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);

        setSupportActionBar(mToolbar);

        //创建ActionBar左边的up action，点击开关左侧抽屉导航
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayut, mToolbar, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayut.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        //默认展示HomeFragment
        showHomeFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("TAG", "onCreateOptionsMenu");
        //创建ActionBar右边Handle actions
        if (mSelectItemId == R.id.leftnavigator_menu_home) {
            getMenuInflater().inflate(R.menu.home_toolbar_actions, menu);
        } else if (mSelectItemId == R.id.leftnavigator_menu_saved) {
            getMenuInflater().inflate(R.menu.saved_toolbar_actions, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i("TAG", "onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("TAG", "onOptionsItemSelected");
        int id = item.getItemId();
        if (id == R.id.toolbar_action_search) {
            Toast.makeText(this, "toolbar_action_search", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toolbar_action_add) {
            Toast.makeText(this, "toolbar_action_add", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toolbar_action_refresh) {
            Toast.makeText(this, "toolbar_action_refresh", Toast.LENGTH_SHORT).show();
//            mHomeFragment.onRefresh();
        } else if (id == R.id.toolbar_action_marked) {
            Toast.makeText(this, "toolbar_action_marked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toolbar_action_rate) {
            Toast.makeText(this, "toolbar_action_rate", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toolbar_action_product) {
            Toast.makeText(this, "toolbar_action_product", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toolbar_action_about) {
            Toast.makeText(this, "toolbar_action_about", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i("TAG", "onNavigationItemSelected");
        mSelectItemId = item.getItemId();

        if (mSelectItemId == R.id.leftnavigator_menu_home) {
            showHomeFragment();
        } else if (mSelectItemId == R.id.leftnavigator_menu_saved) {
            SavedFragment savedFragment = SavedFragment.newInstance();
            mFragmentManager.beginTransaction().replace(R.id.framelayout_fragments, savedFragment).commit();
            setTitle(getResources().getString(R.string.leftnavigator_menu_saved));
        } else if (mSelectItemId == R.id.leftnavigator_menu_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (mSelectItemId == R.id.leftnavigator_menu_feedback) {
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        }

        mDrawerLayut.closeDrawer(GravityCompat.START);
        invalidateOptionsMenu();

        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == mLoginButton) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (view == mLogoutButton) {

        }
    }

    private void showHomeFragment() {
        mHomeFragment = HomeFragment.newInstance();
        mFragmentManager.beginTransaction().replace(R.id.framelayout_fragments, mHomeFragment).commit();
        setTitle(getResources().getString(R.string.leftnavigator_menu_home));
    }
}
