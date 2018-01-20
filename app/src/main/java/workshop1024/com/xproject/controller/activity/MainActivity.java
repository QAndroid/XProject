package workshop1024.com.xproject.controller.activity;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.fragment.TopFragment;
import workshop1024.com.xproject.controller.fragment.XFragment;
import workshop1024.com.xproject.controller.fragment.home.HomeFragment;
import workshop1024.com.xproject.controller.fragment.home.ListFragment;
import workshop1024.com.xproject.controller.fragment.home.PageFragment;
import workshop1024.com.xproject.controller.fragment.saved.SavedFragment;
import workshop1024.com.xproject.model.Story;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View
        .OnClickListener, PageFragment.ContentListItemClickListener, ListFragment.OnStoryListItemClickListener,
        FragmentManager.OnBackStackChangedListener {
    //抽屉导航区域
    //抽屉视图
    private DrawerLayout mDrawerLayut;
    //抽屉列表视图
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
    private XFragment mCurrentFragment;

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

        setSupportActionBar(mToolbar);
        //创建ActionBar左边的up action，点击开关左侧抽屉导航
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayut, mToolbar, R
                .string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayut.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        mActionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mActionBarDrawerToggle.setDrawerSlideAnimationEnabled(false);
        mNavigationView.setNavigationItemSelectedListener(this);

        mLoginButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        showHomeFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        //根据当前显示的Fragment的类型，更新显示的菜单
        if (mCurrentFragment instanceof HomeFragment) {
            menuInflater.inflate(R.menu.home_toolbar_actions, menu);
            setTitle("Paperboy");
        } else if (mCurrentFragment instanceof SavedFragment) {
            menuInflater.inflate(R.menu.saved_toolbar_actions, menu);
            setTitle("Saved");
        } else if (mCurrentFragment instanceof ListFragment) {
            menuInflater.inflate(R.menu.story_toolbar_actions, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        int selectItemId = item.getItemId();

        if (selectItemId == R.id.leftnavigator_menu_home) {
            showHomeFragment();
        } else if (selectItemId == R.id.leftnavigator_menu_saved) {
            SavedFragment savedFragment = SavedFragment.newInstance();
            mFragmentManager.beginTransaction().replace(R.id.main_framelayout_fragments, savedFragment).commit();

            //没有添加到Fragment堆栈管理，则需要单独处理当前显示的Fragment，导航列表选项逻辑
            mNavigationView.setCheckedItem(R.id.leftnavigator_menu_saved);
            mCurrentFragment = savedFragment;
        } else if (selectItemId == R.id.leftnavigator_menu_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (selectItemId == R.id.leftnavigator_menu_feedback) {
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        }

        mDrawerLayut.closeDrawer(GravityCompat.START);
        invalidateOptionsMenu();

        return true;
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
        mCurrentFragment = (XFragment) mFragmentManager.findFragmentById(R.id.main_framelayout_fragments);
        //当MainActivity还显示Fragment的之后执行相关逻辑，MainActivity不展示Fragment相关逻辑
        if (mCurrentFragment != null) {
            //更具当前显示的Fragment多包含的导航栏id，更新导航栏列表选中的选项
            mNavigationView.setCheckedItem(mCurrentFragment.getNavigationItemId());

            //如果是一级Fragment则显示抽屉导航图标，如果只其它级别Fragment这显示返回上一页图标
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(mCurrentFragment instanceof TopFragment ? true : false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(mCurrentFragment instanceof TopFragment ? false : true);
            mActionBarDrawerToggle.syncState();

            //Fragment堆栈有变化，根据当前显示的Fragment重新更新菜单展示
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mLoginButton) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (view == mLogoutButton) {

        }
    }

    @Override
    public void onContentListItemClick(String item) {
        ListFragment listFragment = ListFragment.newInstance();
        mFragmentManager.beginTransaction().replace(R.id.main_framelayout_fragments, listFragment).addToBackStack
                ("").commit();
        setTitle(item);
        mCurrentFragment = listFragment;
    }

    @Override
    public void onListFragmentInteraction(Story story) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }

    private void showHomeFragment() {
        HomeFragment homeFragment = HomeFragment.newInstance();
        mFragmentManager.beginTransaction().replace(R.id.main_framelayout_fragments, homeFragment).commit();

        //没有添加到Fragment堆栈管理，则需要单独处理当前显示的Fragment，导航列表选项逻辑
        mNavigationView.setCheckedItem(R.id.leftnavigator_menu_home);
        mCurrentFragment = homeFragment;
    }
}
