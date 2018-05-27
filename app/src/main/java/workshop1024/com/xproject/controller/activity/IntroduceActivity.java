package workshop1024.com.xproject.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.fragment.introduce.IntroduceFragment;
import workshop1024.com.xproject.view.group.CircleDotIndicator;

/**
 * 介绍页面
 */
public class IntroduceActivity extends FragmentActivity implements View.OnClickListener {
    //介绍内容ViewPager
    private ViewPager mContentViewpager;
    //圆点索引指示器
    private CircleDotIndicator mCricledotindicator;
    //跳过按钮
    private Button mSkipButton;
    //下一步按钮
    private ImageButton mNextButton;
    //完成按钮
    private Button mDoneButton;

    //介绍布局id
    private List<Integer> mLayoutIdList = new ArrayList<>(Arrays.asList(R.layout.introduce1_fragment, R.layout
            .introduce2_fragment, R.layout.introduce3_fragment));
    //介绍ViewPager适配器
    private PagerAdapter mPagerAdapter;

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
        setContentView(R.layout.introduce_activity);

        mContentViewpager = findViewById(R.id.introduce_viewpager_content);
        mCricledotindicator = findViewById(R.id.introduce_cricledotindicator_index);
        //TODO 使用ViewSwitcher优化重构
        mSkipButton = findViewById(R.id.introduce_button_skip);
        mNextButton = findViewById(R.id.introduce_button_next);
        mDoneButton = findViewById(R.id.introduce_button_done);

        mSkipButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mDoneButton.setOnClickListener(this);

        mPagerAdapter = new IntroducePagerAdapter(getSupportFragmentManager(), mLayoutIdList);
        mContentViewpager.setAdapter(mPagerAdapter);
        mContentViewpager.addOnPageChangeListener(new ViewPageChangeListener());

        mCricledotindicator.setViewPager(mContentViewpager);
    }

    @Override
    public void onClick(View view) {
        if (view == mNextButton) {
            toNextViewPageItem();
        } else if (view == mSkipButton || view == mDoneButton) {
            toMainActivity();
        }
    }

    /**
     * 跳转下一个ViewPager的页面
     */
    private void toNextViewPageItem() {
        mContentViewpager.setCurrentItem(mContentViewpager.getCurrentItem() + 1);
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

    /**
     * ViewPager页面切换监听器，处理指示器中圆点的选中和未选中状态切换动画
     */
    private class ViewPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCricledotindicator.setCurrentSelectedCircleDot(position);

            switch (position) {
                case 0:
                case 1:
                    //TODO 是否有某种设计模式可优化
                    mSkipButton.setVisibility(View.VISIBLE);
                    mNextButton.setVisibility(View.VISIBLE);
                    mDoneButton.setVisibility(View.GONE);
                    break;
                case 2:
                    mSkipButton.setVisibility(View.GONE);
                    mNextButton.setVisibility(View.GONE);
                    mDoneButton.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
