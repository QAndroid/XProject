package workshop1024.com.xproject.controller.fragment.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.fragment.TopFragment;
import workshop1024.com.xproject.controller.fragment.XFragment;

/**
 * 抽屉导航Home Fragment，包含ViewPager来显示Stories和Topies子PageFragment
 */
public class HomePageFragment extends XFragment implements TopFragment {
    private String[] mTabTitles;
    private HomeFragmentPagerAdapter mHomeFragmentPagerAdapter;

    public HomePageFragment() {
        // Required empty public constructor
    }

    public static HomePageFragment newInstance() {
        HomePageFragment homePageFragment = new HomePageFragment();
        homePageFragment.setNavigationItemId(R.id.leftnavigator_menu_home);
        return homePageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment, container, false);

        TabLayout tabLayout = view.findViewById(R.id.homepage_tablayout_tabs);
        ViewPager viewPager = view.findViewById(R.id.homepage_viewpager_fragments);

        //设置ViewPager允许有所有的存在屏幕外的Fragment不会被销毁
        mTabTitles = getResources().getStringArray(R.array.homepage_tabs_strings);
        viewPager.setOffscreenPageLimit(mTabTitles.length - 1);

        //不使用getChildFragmentManager，从StoryFragment返回，PageFragment不显示
        mHomeFragmentPagerAdapter = new HomeFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mHomeFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

        return view;
    }

    public void onRefresh() {
        mHomeFragmentPagerAdapter.getCurrentSubFragmet().onRefresh();
    }

    public void markAsRead() {
        mHomeFragmentPagerAdapter.getCurrentSubFragmet().markAsRead();
    }

    /**
     * HomeFragment的ViewPager适配器
     */
    public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private HomeSubFragment mCurrentSubFragmet;

        public HomeFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            if (position == 0) {
                //FIXME 是不是有必要每次都Nes一个Fragment对象
                fragment = SubscribeFragment.newInstance();
            } else if (position == 1) {
                fragment = TagFragment.newInstance();
            } else if (position == 2) {
                fragment = FilterFragment.newInstance();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return mTabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            //获取当前ViewPager显示的Fragment https://blog.csdn.net/hmyang314/article/details/51462677
            mCurrentSubFragmet = (HomeSubFragment) object;
            super.setPrimaryItem(container, position, object);
        }

        public HomeSubFragment getCurrentSubFragmet() {
            return mCurrentSubFragmet;
        }
    }
}