package workshop1024.com.xproject.introduce.introduce

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import workshop1024.com.xproject.introduce.R
import workshop1024.com.xproject.introduce.databinding.IntroduceActivityBinding
import workshop1024.com.xproject.introduce.introduce.viewmodel.IntroduceViewModel

/**
 * 介绍页面
 */
@BindingMethods(value = [BindingMethod(type = ViewPager::class, attribute = "onPageChangeListener", method = "addOnPageChangeListener")])
@Route(path = "/introduce/IntroduceActivity")
class IntroduceActivity : FragmentActivity() {
    //介绍ViewPager适配器
    private lateinit var mPagerAdapter: PagerAdapter

    private lateinit var mIntroduceActivityBinding: IntroduceActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIntroduceActivityBinding = DataBindingUtil.setContentView(this, R.layout.introduce_activity)

        mIntroduceActivityBinding.apply {
            introduceHandlers = IntroduceHandlers()
            introduceviewmodel = ViewModelProviders.of(this@IntroduceActivity).get(IntroduceViewModel::class.java)
            lifecycleOwner = this@IntroduceActivity

            //Smart cast to 'IntroduceViewModel' is impossible, because 'introduceviewmodel' is a mutable property that could have been changed by this time
            //参考：https://stackoverflow.com/questions/44595529/smart-cast-to-type-is-impossible-because-variable-is-a-mutable-property-tha
            introduceviewmodel?.let {
                introduceViewpagerContent.adapter = IntroducePagerAdapter(supportFragmentManager, it.mLayoutIdList)
                introduceViewpagerContent.currentItem = it.mpagePostion.value!!
            }

            introduceCricledotindicatorIndex.setViewPager(introduceViewpagerContent)
        }
    }

    fun showMainActivity() {
        //使用ARouter实现简单跳转，build()路由url，navigation()执行调整转，从而解耦组件间的依赖
        ARouter.getInstance().build("/main/MainActivity").navigation();
    }

    /**
     * 介绍ViewPager适配器
     */
    private inner class IntroducePagerAdapter(fragmentManager: FragmentManager, private val mLayoutIdList: List<Int>)
        : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return IntroduceFragment.newInstance(mLayoutIdList[position])
        }

        override fun getCount(): Int {
            return mLayoutIdList.size
        }
    }

    inner class IntroduceHandlers : ViewPager.OnPageChangeListener {

        fun onClickSkip(view: View) {
            showMainActivity()
        }

        fun onClickNext(view: View) {
            mIntroduceActivityBinding.introduceViewpagerContent.currentItem = mIntroduceActivityBinding.introduceViewpagerContent.currentItem + 1
        }

        fun onClickDone(view: View) {
            showMainActivity()
        }


        override fun onPageSelected(position: Int) {
            mIntroduceActivityBinding.introduceviewmodel?.onPageSelected(position)
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
