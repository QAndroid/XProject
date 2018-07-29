package workshop1024.com.xproject.controller.activity.introduce;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
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
import workshop1024.com.xproject.controller.activity.home.MainActivity;
import workshop1024.com.xproject.controller.fragment.introduce.IntroduceFragment;
import workshop1024.com.xproject.databinding.IntroduceActivityBinding;

@BindingMethods({
        @BindingMethod(type = ViewPager.class, attribute = "onPageChangeListener", method = "addOnPageChangeListener")
})


/**
 * 介绍页面
 */
public class IntroduceActivity extends FragmentActivity {
    //介绍布局id
    private List<Integer> mLayoutIdList = new ArrayList<>(Arrays.asList(R.layout.introduce1_fragment, R.layout
            .introduce2_fragment, R.layout.introduce3_fragment));
    //介绍ViewPager适配器
    private PagerAdapter mPagerAdapter;

    private IntroduceActivityBinding mIntroduceActivityBinding;

    /**
     * 启动介绍页面
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, IntroduceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntroduceActivityBinding = DataBindingUtil.setContentView(this, R.layout.introduce_activity);
        mIntroduceActivityBinding.setIntroduceHandlers(new IntroduceHandlers());

        mPagerAdapter = new IntroducePagerAdapter(getSupportFragmentManager(), mLayoutIdList);
        mIntroduceActivityBinding.introduceViewpagerContent.setAdapter(mPagerAdapter);

        mIntroduceActivityBinding.introduceCricledotindicatorIndex.setViewPager(mIntroduceActivityBinding.
                introduceViewpagerContent);
    }

    /**
     * 跳转下一个ViewPager的页面
     */
    private void toNextViewPageItem() {
        mIntroduceActivityBinding.introduceViewpagerContent.setCurrentItem(mIntroduceActivityBinding.
                introduceViewpagerContent.getCurrentItem() + 1);
    }

    /**
     * 跳转MainActivity页面
     */
    private void toMainActivity() {
        Intent intent = new Intent(IntroduceActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 介绍ViewPager适配器
     */
    private class IntroducePagerAdapter extends FragmentStatePagerAdapter {
        private List<Integer> mLayoutIdList;

        public IntroducePagerAdapter(FragmentManager fragmentManager, List<Integer> layoutIdList) {
            super(fragmentManager);
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

    public class IntroduceHandlers implements ViewPager.OnPageChangeListener {
        public ObservableInt currentPagePosition = new ObservableInt();

        public void onClickSkip(View view) {
            toMainActivity();
        }

        public void onClickNext(View view) {
            toNextViewPageItem();
        }

        public void onClickDone(View view) {
            toMainActivity();
        }

        @Override
        public void onPageSelected(int position) {
            currentPagePosition.set(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
