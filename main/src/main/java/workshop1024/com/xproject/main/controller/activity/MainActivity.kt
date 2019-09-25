package workshop1024.com.xproject.main.controller.activity

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
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.navigation.NavigationView
import org.greenrobot.eventbus.EventBus
import workshop1024.com.xproject.base.arouter.provider.SaveProvider
import workshop1024.com.xproject.base.controller.event.*
import workshop1024.com.xproject.base.controller.fragment.TopFragment
import workshop1024.com.xproject.base.controller.fragment.XFragment
import workshop1024.com.xproject.base.exception.IntentNotFoundException
import workshop1024.com.xproject.base.service.ServiceFactory
import workshop1024.com.xproject.base.utils.IntentUtils
import workshop1024.com.xproject.base.utils.ReflectUtils
import workshop1024.com.xproject.main.R
import workshop1024.com.xproject.main.databinding.MainActivityBinding
import workshop1024.com.xproject.main.databinding.MainLeftnavigatorHeaderBinding
import workshop1024.com.xproject.main.view.popupwindow.BottomMenu
import kotlin.jvm.internal.Reflection

/**
 * 主页面，包含抽屉导航栏，以及导航菜单对应的各个子Fragment页面
 */
//ARouter：在支持路由的页面上添加注解(必选) ，这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/main/MainActivity")
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, FragmentManager.OnBackStackChangedListener {
    private lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle

    private var mCurrentFragment: XFragment? = null

    private lateinit var mMainActivityBinding: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        mMainActivityBinding.mainHandlers = MainHandlers()

        val mainLeftNavigatorHeaderBinding = DataBindingUtil.inflate<MainLeftnavigatorHeaderBinding>(layoutInflater,
                R.layout.main_leftnavigator_header, mMainActivityBinding.mainleftNavigationview, false)
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
        //反射动态获取homePageFragment实例对象，避免对HomePageFragment类的直接依赖
        try {
            //Caused by: f.a.a.b.i.b: 你调用的方法:newInstance，所在的类:workshop1024.com.xproject.home.controller.fragment.HomePageFragment不存在!
            //反射调用，需要配置混淆忽

            //FIXME 已经配置了混淆忽略，发射还继续报错!!
            //Caused by: a.d
            //        at workshop1024.com.xproject.base.utils.ReflectUtils.java.lang.Object invokeCompanionMethod(java.lang.String,java.lang.String,java.lang.Object[])(:7)
            val homePageFragment = ReflectUtils.invokeCompanionMethod("workshop1024.com.xproject.home.controller.fragment.HomePageFragment",
                    "newInstance", R.id.leftnavigator_menu_home) as XFragment

            supportFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, homePageFragment).commit()

            //没有添加到Fragment堆栈管理，则需要单独处理当前显示的Fragment，导航列表选项逻辑
            mMainActivityBinding.mainleftNavigationview.setCheckedItem(R.id.leftnavigator_menu_home)
            mCurrentFragment = homePageFragment
        } catch (exception: ClassNotFoundException) {
            Log.e("XProject", "反射获取homePageFragment实例失败，调用类路径不存在！")
            exception.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //根据当前显示的Fragment的类型，更新显示的菜单
        //通过公共组件接口方式，实现组件之间的通信
        //FIXME ? !!等如何合理的设置和配置，不能总是靠提示让代码勉强运行
        if (ServiceFactory.getInstance()?.homeService?.isHomePageFragment(mCurrentFragment)!!) {
            menuInflater.inflate(R.menu.homepage_toolbar_menu, menu)
            setTitle(R.string.toolbar_title_home)
            val searchView = menu?.findItem(R.id.homepage_menu_search)?.actionView as SearchView
            searchView.setOnQueryTextListener(this)
        } else if (ServiceFactory.getInstance()?.homeService?.isNewsListFragment(mCurrentFragment)!!) {
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
                try {
                    IntentUtils.startActivityByNewIntent(this, "workshop1024.com.xproject.introduce.controller.activity.IntroduceActivity")
                } catch (exception: IntentNotFoundException) {
                    exception.printStackTrace();
                    Log.i("XProject", "跳转介绍页面的意图不存在！")
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
        //FIXME 智能通过类型转换吗？？
        val searchNewsFragment = query?.let {
            ReflectUtils.invokeCompanionMethod("workshop1024.com.xproject.home.controller.fragment.news.SearchNewsFragment",
                    "newInstance", it, R.id.leftnavigator_menu_home)
        } as XFragment
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
                    //动态创建Fragment:ARouter，跨组件创建savedFragment实例
                    //使用依赖查找发现线服务，通过SaveProvider::class.java类型查找到saveProvider如无
                    val saveProvider = ARouter.getInstance().navigation(SaveProvider::class.java)
                    val savedFragment = saveProvider.newSavedFragmentInstance(R.id.leftnavigator_menu_saved)
                    supportFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, savedFragment).commit()

                    //没有添加到Fragment堆栈管理，则需要单独处理当前显示的Fragment，导航列表选项逻辑
                    mMainActivityBinding.mainleftNavigationview.setCheckedItem(R.id.leftnavigator_menu_saved)
                    mCurrentFragment = savedFragment as XFragment
                }
                R.id.leftnavigator_menu_settings -> {
                    //隐式意图2-setClass(content,...)，通过公共组件实现依赖
                    try {
                        IntentUtils.startActivityBySetClassName(this@MainActivity, "workshop1024.com.xproject.settings.controller.activity.SettingsActivity")
                    } catch (exception: IntentNotFoundException) {
                        exception.printStackTrace()
                        Log.e("XProject", "跳转SettingsActivity的意图不存在！")
                    }
                }
                R.id.leftnavigator_menu_feedback -> {
                    //隐式意图3-setComponent，避免组件直接依赖
                    try {
                        IntentUtils.startActivityBySetComponent(this@MainActivity, "workshop1024.com.xproject.feedback.controller.activity.FeedbackActivity")
                    } catch (exception: IntentNotFoundException) {
                        exception.printStackTrace()
                        Log.e("XProject", "跳转FeedbackActivity的意图不存在！")
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
            //ARouter：应用内简单的跳转，build填写地址，navigation发射路由
            ARouter.getInstance().build("/login/LoginActivity").navigation()
        }

        fun onClickLogout(view: View) {
            Log.i("XProject", "onClickLogout")
        }
    }
}