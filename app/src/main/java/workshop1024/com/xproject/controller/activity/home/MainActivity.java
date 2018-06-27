package workshop1024.com.xproject.controller.activity.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.feedback.FeedbackActivity;
import workshop1024.com.xproject.controller.activity.introduce.IntroduceActivity;
import workshop1024.com.xproject.controller.activity.login.LoginActivity;
import workshop1024.com.xproject.controller.activity.setting.SettingsActivity;
import workshop1024.com.xproject.controller.fragment.TopFragment;
import workshop1024.com.xproject.controller.fragment.XFragment;
import workshop1024.com.xproject.controller.fragment.home.HomePageFragment;
import workshop1024.com.xproject.controller.fragment.home.news.NewsListFragment;
import workshop1024.com.xproject.controller.fragment.home.news.SearchNewsFragment;
import workshop1024.com.xproject.controller.fragment.save.SavedFragment;
import workshop1024.com.xproject.view.popupwindow.BottomMenu;

/**
 * 主页面，包含抽屉导航栏，以及导航菜单对应的各个子Fragment页面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener,
        NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {
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
    private CoordinatorLayout mRightCoordinatorLayout;
    //工具栏
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private FloatingActionButton mFloatingActionButton;

    private FragmentManager mFragmentManager;
    private XFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mDrawerLayut = findViewById(R.id.main_drawerlayout_navigator);
        mNavigationView = findViewById(R.id.mainleft_navigationview);
        mRightCoordinatorLayout = findViewById(R.id.mainright_coordinatorlayout_root);
        mToolbar = findViewById(R.id.main_toolbar_navigator);
        mFloatingActionButton = findViewById(R.id.mainright_floatingactionbutton_action);

        View headerLayout = mNavigationView.inflateHeaderView(R.layout.mainleft_navigator_header);
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

        mFloatingActionButton.setOnClickListener(this);

        mLoginButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        showHomePageFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        //根据当前显示的Fragment的类型，更新显示的菜单
        if (mCurrentFragment instanceof HomePageFragment) {
            menuInflater.inflate(R.menu.homepage_toolbar_menu, menu);
            setTitle(R.string.toolbar_title_home);
            SearchView searchView = (SearchView) menu.findItem(R.id.homepage_menu_search).getActionView();
            searchView.setOnQueryTextListener(this);
        } else if (mCurrentFragment instanceof NewsListFragment) {
            menuInflater.inflate(R.menu.newslist_toolbar_menu, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homepage_menu_add:
                BottomMenu bottomMenu = new BottomMenu(this);
                bottomMenu.showAtLocation(mRightCoordinatorLayout, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.homepage_menu_refresh:
                ((HomePageFragment) mCurrentFragment).onRefresh();
                break;
            case R.id.homepage_menu_marked:
                ((HomePageFragment) mCurrentFragment).markAsRead();
                break;
            case R.id.homepage_menu_product:
                IntroduceActivity.startActivity(this);
                finish();
                break;
            case R.id.homepage_menu_about:
                AboutActivity.startActivity(this);
                break;
            case R.id.newslist_menu_refresh:
                ((NewsListFragment) mCurrentFragment).onRefresh();
                break;
            case R.id.newslist_menu_cards:
                ((NewsListFragment) mCurrentFragment).showBigCardsList();
                break;
            case R.id.newslist_menu_compact:
                ((NewsListFragment) mCurrentFragment).showCompactList();
                break;
            case R.id.newslist_menu_minimal:
                ((NewsListFragment) mCurrentFragment).showMinimalList();
                break;
            case R.id.newslist_menu_marked:
                ((NewsListFragment) mCurrentFragment).markAsRead();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int selectItemId = item.getItemId();

        if (selectItemId == R.id.leftnavigator_menu_home) {
            showHomePageFragment();
        } else if (selectItemId == R.id.leftnavigator_menu_saved) {
            SavedFragment savedFragment = SavedFragment.newInstance();
            mFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, savedFragment).commit();

            //没有添加到Fragment堆栈管理，则需要单独处理当前显示的Fragment，导航列表选项逻辑
            mNavigationView.setCheckedItem(R.id.leftnavigator_menu_saved);
            mCurrentFragment = savedFragment;
        } else if (selectItemId == R.id.leftnavigator_menu_settings) {
            SettingsActivity.startActivity(this);
        } else if (selectItemId == R.id.leftnavigator_menu_feedback) {
            FeedbackActivity.startActivity(this);
        }

        mDrawerLayut.closeDrawer(GravityCompat.START);
        invalidateOptionsMenu();

        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
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
        mCurrentFragment = (XFragment) mFragmentManager.findFragmentById(R.id.mainright_framelayout_fragments);
        //当MainActivity还显示Fragment的之后执行相关逻辑，MainActivity不展示Fragment相关逻辑
        if (mCurrentFragment != null) {
            //更具当前显示的Fragment多包含的导航栏id，更新导航栏列表选中的选项
            mNavigationView.setCheckedItem(mCurrentFragment.getNavigationItemId());

            //如果是一级Fragment则显示抽屉导航图标和隐藏FloatingActionButton，如果只其它级别Fragment这显示返回上一页图标
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(mCurrentFragment instanceof TopFragment ? true : false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(mCurrentFragment instanceof TopFragment ? false : true);
            mActionBarDrawerToggle.syncState();
            mFloatingActionButton.setVisibility(mCurrentFragment instanceof TopFragment ? View.GONE : View.VISIBLE);

            //Fragment堆栈有变化，根据当前显示的Fragment重新更新菜单展示
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mLoginButton) {
            LoginActivity.startActivity(this);
        } else if (view == mLogoutButton) {

        } else if (view == mFloatingActionButton) {
            //FIXME 这种实现是不是很别扭??
            if (mCurrentFragment instanceof NewsListFragment) {
                ((NewsListFragment) mCurrentFragment).markAsRead();
            } else {
                ((SavedFragment) mCurrentFragment).markAsRead();
            }
        }
    }

    private void showHomePageFragment() {
        HomePageFragment homePageFragment = HomePageFragment.newInstance();
        mFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, homePageFragment).commit();

        //没有添加到Fragment堆栈管理，则需要单独处理当前显示的Fragment，导航列表选项逻辑
        mNavigationView.setCheckedItem(R.id.leftnavigator_menu_home);
        mCurrentFragment = homePageFragment;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        SearchNewsFragment searchNewsFragment = SearchNewsFragment.newInstance(query);
        mFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, searchNewsFragment)
                .addToBackStack("").commit();
        setTitle(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i("XProject", "onQueryTextChange =" + newText);
        return false;
    }
}
