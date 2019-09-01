package workshop1024.com.xproject.home.controller.activity

import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.core.view.GravityCompat
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import org.greenrobot.eventbus.EventBus
import workshop1024.com.xproject.base.controller.fragment.TopFragment
import workshop1024.com.xproject.base.controller.fragment.XFragment
import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.controller.event.*
import workshop1024.com.xproject.home.controller.fragment.HomePageFragment
import workshop1024.com.xproject.home.controller.fragment.news.NewsListFragment
import workshop1024.com.xproject.home.controller.fragment.news.SearchNewsFragment
import workshop1024.com.xproject.home.controller.fragment.save.SavedFragment
import workshop1024.com.xproject.home.databinding.MainActivityBinding
import workshop1024.com.xproject.home.databinding.MainleftNavigatorHeaderBinding
import workshop1024.com.xproject.home.view.popupwindow.BottomMenu

/**
 * 主页面，包含抽屉导航栏，以及导航菜单对应的各个子Fragment页面
 */
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, FragmentManager.OnBackStackChangedListener {
    private lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle

    private var mCurrentFragment: XFragment? = null

    private lateinit var mMainActivityBinding: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        mMainActivityBinding.mainHandlers = MainHandlers()

        val mainLeftNavigatorHeaderBinding = DataBindingUtil.inflate<MainleftNavigatorHeaderBinding>(layoutInflater,
                R.layout.mainleft_navigator_header, mMainActivityBinding.mainleftNavigationview, false)
        mainLeftNavigatorHeaderBinding.headerHandlers = HeaderHandlers()

        //设置抽屉导航HeaderView视图
        mMainActivityBinding.mainleftNavigationview.addHeaderView(mainLeftNavigatorHeaderBinding.root)

        //设置顶部toolBar视图
        setSupportActionBar(mMainActivityBinding.mainIncludeRight.mainrightToolbarNavigator)

        //创建ActionBar左边的up action，点击开关左侧抽屉导航
        mActionBarDrawerToggle = ActionBarDrawerToggle(this, mMainActivityBinding.mainDrawerlayoutNavigator,
                mMainActivityBinding.mainIncludeRight.mainrightToolbarNavigator, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        mMainActivityBinding.mainDrawerlayoutNavigator.addDrawerListener(mActionBarDrawerToggle)
        mActionBarDrawerToggle.syncState()
        mActionBarDrawerToggle.setToolbarNavigationClickListener {
            View.OnClickListener {
                onBackPressed()
            }
        }
        mActionBarDrawerToggle.isDrawerSlideAnimationEnabled = false

        //处理Fragment的交互显示，默认显示HomeFragment
        supportFragmentManager.addOnBackStackChangedListener(this)
        showHomePageFragment()
    }

    private fun showHomePageFragment() {
        val homePageFragment = HomePageFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, homePageFragment).commit()

        //没有添加到Fragment堆栈管理，则需要单独处理当前显示的Fragment，导航列表选项逻辑
        mMainActivityBinding.mainleftNavigationview.setCheckedItem(R.id.leftnavigator_menu_home)
        mCurrentFragment = homePageFragment
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //根据当前显示的Fragment的类型，更新显示的菜单
        if (mCurrentFragment is HomePageFragment) {
            menuInflater.inflate(R.menu.homepage_toolbar_menu, menu)
            setTitle(R.string.toolbar_title_home)
            val searchView = menu?.findItem(R.id.homepage_menu_search)?.actionView as SearchView
            searchView.setOnQueryTextListener(this)
        } else if (mCurrentFragment is NewsListFragment) {
            menuInflater.inflate(R.menu.newslist_toolbar_menu, menu)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.homepage_menu_add -> {
                val bottomMenu = BottomMenu(this)
                bottomMenu.showAtLocation(mMainActivityBinding.mainIncludeRight.mainrightCoordinatorlayoutRoot, Gravity.BOTTOM, 0, 0)
            }
            R.id.homepage_menu_refresh -> EventBus.getDefault().post(HomePageRefreshEvent())
            R.id.homepage_menu_marked -> EventBus.getDefault().post(HomePageAsReadEvent())
            R.id.homepage_menu_product -> {
                val intent = Intent("workshop1024.com.xproject.introduce.controller.activity.IntroduceActivity")
                //检验隐式意图，安全调用
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }
            R.id.homepage_menu_about -> AboutActivity.startActivity(this)
            R.id.newslist_menu_cards -> EventBus.getDefault().post(NewsListShowBigCardsEvent())
            R.id.newslist_menu_compact -> EventBus.getDefault().post(NewsListShowCompactEvent())
            R.id.newslist_menu_minimal -> EventBus.getDefault().post(NewsListShowMinimalEvent())
            R.id.newslist_menu_marked -> EventBus.getDefault().post(NewsListAsReadEvent())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mActionBarDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        mActionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onBackStackChanged() {
        mCurrentFragment = supportFragmentManager.findFragmentById(R.id.mainright_framelayout_fragments) as XFragment?
        //当MainActivity还显示Fragment的之后执行相关逻辑，MainActivity不展示Fragment相关逻辑

        if (mCurrentFragment != null) {
            //更具当前显示的Fragment多包含的导航栏id，更新导航栏列表选中的选项
            mMainActivityBinding.mainleftNavigationview.setCheckedItem(mCurrentFragment!!.mNavigationItemId)

            //如果是一级Fragment则显示抽屉导航图标和隐藏FloatingActionButton，如果只其它级别Fragment这显示返回上一页图标
            mActionBarDrawerToggle.isDrawerSlideAnimationEnabled = mCurrentFragment is TopFragment
            supportActionBar?.setDisplayHomeAsUpEnabled(mCurrentFragment !is TopFragment)
            mActionBarDrawerToggle.syncState()

            mMainActivityBinding.mainIncludeRight.mainrightFloatingactionbuttonAction.visibility = if (mCurrentFragment is TopFragment) View.GONE
            else View.VISIBLE

            //Fragment堆栈有变化，根据当前显示的Fragment重新更新菜单展示
            invalidateOptionsMenu()
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        val searchNewsFragment = SearchNewsFragment.newInstance(query!!)
        supportFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, searchNewsFragment).addToBackStack("").commit()
        title = query

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


    inner class MainHandlers : NavigationView.OnNavigationItemSelectedListener {
        fun onClickAction(view: View) {
            EventBus.getDefault().post(NewsListAsReadEvent())
        }

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.leftnavigator_menu_home -> showHomePageFragment()
                R.id.leftnavigator_menu_saved -> {
                    val savedFragment = SavedFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, savedFragment).commit()

                    //没有添加到Fragment堆栈管理，则需要单独处理当前显示的Fragment，导航列表选项逻辑
                    mMainActivityBinding.mainleftNavigationview.setCheckedItem(R.id.leftnavigator_menu_saved)
                    mCurrentFragment = savedFragment
                }
                R.id.leftnavigator_menu_settings -> {
                    //隐式意图2-setClass(content,...)，避免组件间的直接依赖，通过公共组件实现依赖
                    val intent = Intent()
                    intent.setClassName(this@MainActivity, "workshop1024.com.xproject.settings.controller.activity.SettingsActivity")
                    //检验隐式意图，安全调用
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
                R.id.leftnavigator_menu_feedback -> {
                    //隐式意图3-setComponent，避免组件直接依赖
                    val intent = Intent()
                    intent.setComponent(ComponentName(this@MainActivity.packageName, "workshop1024.com.xproject.feedback.controller.activity.FeedbackActivity"))
                    //检验隐式意图，安全调用
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
            }

            mMainActivityBinding.mainDrawerlayoutNavigator.closeDrawer(GravityCompat.START)
            invalidateOptionsMenu()

            return true
        }

    }

    inner class HeaderHandlers {
        fun onClickLogin(view: View) {
//            LoginActivity.startActivity(this@MainActivity)
        }

        fun onClickLogout(view: View) {
            Log.i("XProject", "onClickLogout")
        }
    }
}