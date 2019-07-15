package workshop1024.com.xproject.controller.fragment.home.news;

import android.os.Bundle;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.fragment.SubFragment;

public class FilterNewsFragment extends NewsListFragment implements SubFragment {
    private static final String FILTER_NAME = "filter_Name";
    private String mFilterName;

    public static FilterNewsFragment newInstance(String filterName) {
        FilterNewsFragment filterNewsFragment = new FilterNewsFragment();
        filterNewsFragment.setMNavigationItemId(R.id.leftnavigator_menu_home);
        Bundle args = new Bundle();
        args.putString(FILTER_NAME, filterName);
        filterNewsFragment.setArguments(args);
        return filterNewsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFilterName = getArguments().getString(FILTER_NAME);
        }
    }

    @Override
    public void getNewsList() {
        mNewsRepository.getNewsListByFilter(mFilterName, this);
    }
}
