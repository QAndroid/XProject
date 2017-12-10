package workshop1024.com.xproject.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
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
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<String> mTitleList = new ArrayList<String>() {{
        add("POPULAR");
        add("STORIES");
    }};
    private List<View> mViewList = new ArrayList<>();

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
        mTabLayout = view.findViewById(R.id.home_tablayout);
        mViewPager = view.findViewById(R.id.home_viewpager);
        View view1 = getLayoutInflater().inflate(R.layout.view1, null);
        View view2 = getLayoutInflater().inflate(R.layout.view2, null);
        mViewList.add(view1);
        mViewList.add(view2);

        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        MyAdapter myAdapter = new MyAdapter(mTitleList, mViewList);
        mTabLayout.setTabsFromPagerAdapter(myAdapter);
        mViewPager.setAdapter(myAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);

        return view;
    }

    public class MyAdapter extends PagerAdapter {

        private List<String> mTitleList;
        private List<View> mViewList;

        public MyAdapter(List<String> titleList, List<View> viewList) {
            mTitleList = titleList;
            mViewList = viewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}
