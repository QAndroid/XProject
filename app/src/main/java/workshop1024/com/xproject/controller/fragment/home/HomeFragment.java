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

/**
 * 主页Fragment
 */
public class HomeFragment extends TopFragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setNavigationItemId(R.id.leftnavigator_menu_home);
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        TabLayout tabLayout = view.findViewById(R.id.home_tablayout_tabs);
        ViewPager viewPager = view.findViewById(R.id.home_viewpager_fragments);

        //不使用getChildFragmentManager，从StoryFragment返回，PageFragment不显示
        PagerFragmentPagerAdapter pagerFragmentPagerAdapter = new PagerFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

        return view;
    }

    public class PagerFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private String mTabTitles[] = new String[]{"STORIES", "TOPICS"};

        public PagerFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance();
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
