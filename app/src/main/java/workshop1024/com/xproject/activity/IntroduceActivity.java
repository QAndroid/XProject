package workshop1024.com.xproject.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.IntroduceActivityBinding;
import workshop1024.com.xproject.fragment.IntroduceFragment;

/**
 * 介绍页面
 */
public class IntroduceActivity extends FragmentActivity {
    //介绍布局id
    List<Integer> mLayoutIdList = new ArrayList<>(Arrays.asList(R.layout.introduce1_fragment, R.layout
            .introduce2_fragment, R.layout.introduce3_fragment));
    //介绍ViewPager适配器
    private PagerAdapter mPagerAdapter;

    //页面数据绑定类
    private IntroduceActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.introduce_activity);

        mPagerAdapter = new IntroducePagerAdapter(getSupportFragmentManager(), mLayoutIdList);
        mBinding.contentViewpager.setAdapter(mPagerAdapter);
        mBinding.contentViewpager.addOnPageChangeListener(new ViewPageChangeListener());

        mBinding.indexCricledotindicator.setViewPager(mBinding.contentViewpager);

        Presenter presenter = new Presenter();
        mBinding.setPresenter(presenter);
    }

    /**
     * 跳转下一个ViewPager的页面
     */
    private void toNextViewPageItem() {
        mBinding.contentViewpager.setCurrentItem(mBinding.contentViewpager.getCurrentItem() + 1);
    }

    /**
     * 跳转MainActivity页面
     */
    private void toMainActivity() {
        Intent intent = new Intent(IntroduceActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * 介绍ViewPager适配器
     */
    private class IntroducePagerAdapter extends FragmentStatePagerAdapter {
        private List<Integer> mLayoutIdList;

        public IntroducePagerAdapter(FragmentManager fm, List<Integer> layoutIdList) {
            super(fm);
            mLayoutIdList = layoutIdList;
        }

        @Override
        public Fragment getItem(int position) {
            return IntroduceFragment.newInstance(mLayoutIdList.get(position));
        }

        @Override
        public int getCount() {
            return mLayoutIdList.size();
        }
    }

    /**
     * ViewPager页面切换监听器，处理指示器中圆点的选中和未选中状态切换动画
     */
    private class ViewPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mBinding.indexCricledotindicator.setCurrentSelectedCircleDot(position);

            switch (position) {
                case 0:
                case 1:
                    //TODO 是否有某种设计模式可优化
                    mBinding.skipButton.setVisibility(View.VISIBLE);
                    mBinding.nextButton.setVisibility(View.VISIBLE);
                    mBinding.doneButton.setVisibility(View.GONE);
                    break;
                case 2:
                    mBinding.skipButton.setVisibility(View.GONE);
                    mBinding.nextButton.setVisibility(View.GONE);
                    mBinding.doneButton.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //数据绑定-事件绑定-监听器绑定
    public class Presenter {
        public void onSkipButtonClick() {
            toMainActivity();
        }

        public void onNextButtonClick() {
            toNextViewPageItem();
        }

        public void onDoneButtonClick() {
            toMainActivity();
        }
    }
}
