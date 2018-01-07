package workshop1024.com.xproject.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import workshop1024.com.xproject.fragment.StoryFragment;
import workshop1024.com.xproject.fragment.dummy.DummyContent;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View
        .OnClickListener, HomeFragment.ContentListItemClickListener, StoryFragment.OnListFragmentInteractionListener,
        FragmentManager.OnBackStackChangedListener {
    //抽屉导航区域
    //抽屉视图
    private DrawerLayout mDrawerLayut;
    //导航视图
    private NavigationView mNavigationView;
    //登陆按钮
    private Button mLoginButton;
    //退出按钮
    private ImageButton mLogoutButton;

    //内容区域
    //工具栏
    private Toolbar mToolbar;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;

    private int mSelectItemId = R.id.leftnavigator_menu_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mDrawerLayut = findViewById(R.id.main_drawerlayout_navigator);
        mNavigationView = findViewById(R.id.main_navigationview_left);
        mToolbar = findViewById(R.id.main_toolbar_navigator);

        View headerLayout = mNavigationView.inflateHeaderView(R.layout.main_leftnavigator_header);
        mLoginButton = headerLayout.findViewById(R.id.leftnavigator_button_login);
        mLogoutButton = headerLayout.findViewById(R.id.leftnavigator_imagebutton_logout);

        mLoginButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);

        setSupportActionBar(mToolbar);

        //创建ActionBar左边的up action，点击开关左侧抽屉导航
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayut, mToolbar, R
                .string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayut.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

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
        } else if (mSelectItemId == 0x001) {
            getMenuInflater().inflate(R.menu.story_toolbar_actions, menu);
        }
        return super.onCreateOptionsMenu(menu);
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
            mFragmentManager.beginTransaction().replace(R.id.main_framelayout_fragments, savedFragment).addToBackStack
                    ("").commit();
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
        mFragmentManager.beginTransaction().replace(R.id.main_framelayout_fragments, mHomeFragment).addToBackStack("")
                .commit();
        setTitle(getResources().getString(R.string.leftnavigator_menu_home));
        mNavigationView.setCheckedItem(R.id.leftnavigator_menu_home);
    }

    @Override
    public void onContentListItemClick(String item) {
        StoryFragment storyFragment = StoryFragment.newInstance(1);
        mFragmentManager.beginTransaction().replace(R.id.main_framelayout_fragments, storyFragment).addToBackStack
                ("").commit();
        setTitle(item);
        mToolbar.setNavigationIcon(R.drawable.login_icon);

        mSelectItemId = 0x001;
        invalidateOptionsMenu();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackStackChanged() {
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(mFragmentManager.getBackStackEntryCount() == 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(mFragmentManager.getBackStackEntryCount() > 0);
        mActionBarDrawerToggle.syncState();
    }
}
