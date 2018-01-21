package workshop1024.com.xproject.controller.fragment;

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

/**
 * 抽屉导航Home Fragment，包含ViewPager来显示Stories和Topies子PageFragment
 */
public class HomePageFragment extends TopFragment {

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

        //不使用getChildFragmentManager，从StoryFragment返回，PageFragment不显示
        HomeFragmentPagerAdapter homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(homeFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

        return view;
    }

    /**
     * HomeFragment的ViewPager适配器
     */
    public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private String[] mTabTitles;

        public HomeFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            mTabTitles = getResources().getStringArray(R.array.homepage_tabs_strings);
        }

        @Override
        public Fragment getItem(int position) {
            return PageAdapterFragment.newInstance();
        }

        @Override
        public int getCount() {
            return mTabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }
}
