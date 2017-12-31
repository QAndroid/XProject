package workshop1024.com.xproject.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;

/**
 * 主页Fragment
 */
public class HomeFragment extends Fragment {
    //主页Tab标签
    private TabLayout mTabLayoutTabs;
    //主页Tab内容
    private ViewPager mViewPagerFragments;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mTabLayoutTabs = view.findViewById(R.id.home_tablayout_tabs);
        mViewPagerFragments = view.findViewById(R.id.home_viewpager_fragments);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPagerFragments.setAdapter(viewPagerAdapter);
        mTabLayoutTabs.setupWithViewPager(mViewPagerFragments, true);

        return view;
    }

    /**
     * ViewPager适配器，创建HomeSubFragment
     */
    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<String> mTabTitleList = new ArrayList<String>() {{
            add("STORIES");
            add("POPULAR");
        }};

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return HomeSubFragment.newInstance();
        }

        @Override
        public int getCount() {
            return mTabTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitleList.get(position);
        }
    }
}
