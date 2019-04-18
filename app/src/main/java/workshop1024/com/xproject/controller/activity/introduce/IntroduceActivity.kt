package workshop1024.com.xproject.controller.activity.introduce

import android.content.Context
import android.content.Intent
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.databinding.DataBindingUtil
import android.databinding.ObsergvableInt
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View

import java.util.ArrayList
import java.util.Arrays

import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.activity.home.MainActivity
import workshop1024.com.xproject.controller.fragment.introduce.IntroduceFragment
import workshop1024.com.xproject.databinding.IntroduceActivityBinding

@BindingMethods(BindingMethod(type = ViewPager::class, attribute = "onPageChangeListener", method = "addOnPageChangeListener"))
/**
 * 介绍页面
 */
class IntroduceActivity : FragmentActivity() {
    //介绍布局id
    private val mLayoutIdList = ArrayList(Arrays.asList(R.layout.introduce1_fragment, R.layout
            .introduce2_fragment, R.layout.introduce3_fragment))
    //介绍ViewPager适配器
    private var mPagerAdapter: PagerAdapter? = null

    private var mIntroduceActivityBinding: IntroduceActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIntroduceActivityBinding = DataBindingUtil.setContentView(this, R.layout.introduce_activity)
        mIntroduceActivityBinding!!.introduceHandlers = IntroduceHandlers()

        mPagerAdapter = IntroducePagerAdapter(supportFragmentManager, mLayoutIdList)
        mIntroduceActivityBinding!!.introduceViewpagerContent.adapter = mPagerAdapter

        mIntroduceActivityBinding!!.introduceCricledotindicatorIndex.setViewPager(mIntroduceActivityBinding!!.introduceViewpagerContent)
    }

    /**
     * 跳转下一个ViewPager的页面
     */
    private fun toNextViewPageItem() {
        mIntroduceActivityBinding!!.introduceViewpagerContent.currentItem = mIntroduceActivityBinding!!.introduceViewpagerContent.currentItem + 1
    }

    /**
     * 跳转MainActivity页面
     */
    private fun toMainActivity() {
        val intent = Intent(this@IntroduceActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * 介绍ViewPager适配器
     */
    private inner class IntroducePagerAdapter(fragmentManager: FragmentManager, private val mLayoutIdList: List<Int>) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return IntroduceFragment.newInstance(mLayoutIdList[position])
        }

        override fun getCount(): Int {
            return mLayoutIdList.size
        }
    }

    inner class IntroduceHandlers : ViewPager.OnPageChangeListener {
        //可观察的ViewPager当前页面索引，用于布局中按钮和指示器的更新
        var currentPagePosition = ObservableInt()

        fun onClickSkip(view: View) {
            toMainActivity()
        }

        fun onClickNext(view: View) {
            toNextViewPageItem()
        }

        fun onClickDone(view: View) {
            toMainActivity()
        }

        override fun onPageSelected(position: Int) {
            currentPagePosition.set(position)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }

    companion object {

        /**
         * 启动介绍页面
         *
         * @param context
         */
        fun startActivity(context: Context) {
            val intent = Intent(context, IntroduceActivity::class.java)
            context.startActivity(intent)
        }
    }

}
