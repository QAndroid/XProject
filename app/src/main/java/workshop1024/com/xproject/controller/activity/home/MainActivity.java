package workshop1024.com.xproject.controller.activity.home;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.feedback.FeedbackActivity;
import workshop1024.com.xproject.controller.activity.introduce.IntroduceActivity;
import workshop1024.com.xproject.controller.activity.login.LoginActivity;
import workshop1024.com.xproject.controller.activity.settings.SettingsActivity;
import workshop1024.com.xproject.controller.fragment.TopFragment;
import workshop1024.com.xproject.controller.fragment.XFragment;
import workshop1024.com.xproject.controller.fragment.home.HomePageFragment;
import workshop1024.com.xproject.controller.fragment.home.news.NewsListFragment;
import workshop1024.com.xproject.controller.fragment.home.news.SearchNewsFragment;
import workshop1024.com.xproject.controller.fragment.save.SavedFragment;
import workshop1024.com.xproject.databinding.MainActivityBinding;
import workshop1024.com.xproject.databinding.MainleftNavigatorHeaderBinding;
import workshop1024.com.xproject.view.popupwindow.BottomMenu;

/**
 * 主页面，包含抽屉导航栏，以及导航菜单对应的各个子Fragment页面
 */
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        FragmentManager.OnBackStackChangedListener {
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private FragmentManager mFragmentManager;
    private XFragment mCurrentFragment;

    private MainActivityBinding mMainActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        mMainActivityBinding.setMainHandlers(new MainHandlers());

        MainleftNavigatorHeaderBinding mainLeftNavigatorHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.mainleft_navigator_header, mMainActivityBinding.mainleftNavigationview, false);
        mainLeftNavigatorHeaderBinding.setHeaderHandlers(new HeaderHandlers());

        //设置抽屉导航HeaderView视图
        mMainActivityBinding.mainleftNavigationview.addHeaderView(mainLeftNavigatorHeaderBinding.getRoot());

        //设置顶部toolBar视图
        setSupportActionBar(mMainActivityBinding.mainIncludeRight.mainrightToolbarNavigator);

        //创建ActionBar左边的up action，点击开关左侧抽屉导航
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mMainActivityBinding.mainDrawerlayoutNavigator,
                mMainActivityBinding.mainIncludeRight.mainrightToolbarNavigator, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mMainActivityBinding.mainDrawerlayoutNavigator.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        mActionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mActionBarDrawerToggle.setDrawerSlideAnimationEnabled(false);

        //处理Fragment的交互显示，默认显示HomeFragment
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
                bottomMenu.showAtLocation(mMainActivityBinding.mainIncludeRight.mainrightCoordinatorlayoutRoot,
                        Gravity.BOTTOM, 0, 0);
                break;
            case R.id.homepage_menu_refresh:
                ((HomePageFragment) mCurrentFragment).onRefresh();
                break;
            case R.id.homepage_menu_marked:
                ((HomePageFragment) mCurrentFragment).markAsRead();
                break;
            case R.id.homepage_menu_product:
                IntroduceActivity.Companion.startActivity(this);
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
            mMainActivityBinding.mainleftNavigationview.setCheckedItem(mCurrentFragment.getNavigationItemId());

            //如果是一级Fragment则显示抽屉导航图标和隐藏FloatingActionButton，如果只其它级别Fragment这显示返回上一页图标
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(mCurrentFragment instanceof TopFragment ? true : false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(mCurrentFragment instanceof TopFragment ? false : true);
            mActionBarDrawerToggle.syncState();

            mMainActivityBinding.mainIncludeRight.mainrightFloatingactionbuttonAction.setVisibility(mCurrentFragment
                    instanceof TopFragment ? View.GONE : View.VISIBLE);

            //Fragment堆栈有变化，根据当前显示的Fragment重新更新菜单展示
            invalidateOptionsMenu();
        }
    }

    private void showHomePageFragment() {
        HomePageFragment homePageFragment = HomePageFragment.newInstance();
        mFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, homePageFragment).commit();

        //没有添加到Fragment堆栈管理，则需要单独处理当前显示的Fragment，导航列表选项逻辑
        mMainActivityBinding.mainleftNavigationview.setCheckedItem(R.id.leftnavigator_menu_home);
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
        return false;
    }

    public class MainHandlers implements NavigationView.OnNavigationItemSelectedListener {
        public void onClickAction(View view) {
            if (mCurrentFragment instanceof NewsListFragment) {
                ((NewsListFragment) mCurrentFragment).markAsRead();
            } else {
                ((SavedFragment) mCurrentFragment).markAsRead();
            }
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
                mMainActivityBinding.mainleftNavigationview.setCheckedItem(R.id.leftnavigator_menu_saved);
                mCurrentFragment = savedFragment;
            } else if (selectItemId == R.id.leftnavigator_menu_settings) {
                SettingsActivity.startActivity(MainActivity.this);
            } else if (selectItemId == R.id.leftnavigator_menu_feedback) {
                FeedbackActivity.startActivity(MainActivity.this);
            }

            mMainActivityBinding.mainDrawerlayoutNavigator.closeDrawer(GravityCompat.START);
            invalidateOptionsMenu();

            return true;
        }
    }

    public class HeaderHandlers {
        public void onClickLogin(View view) {
            Log.i("XProject", "onClickLogin");
//            LoginActivity.startActivity(MainActivity.this);
            LoginActivity.Companion.startActivity(MainActivity.this);
        }

        public void onClickLogout(View view) {
            Log.i("XProject", "onClickLogout");
        }
    }
}
