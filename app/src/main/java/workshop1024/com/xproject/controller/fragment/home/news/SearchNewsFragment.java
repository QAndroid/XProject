package workshop1024.com.xproject.controller.fragment.home.news;

import android.os.Bundle;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.fragment.SubFragment;

public class SearchNewsFragment extends NewsListFragment implements SubFragment {
    private static final String SEARCH_NAME = "search_Name";
    private String mFilterName;

    public static SearchNewsFragment newInstance(String searchName) {
        SearchNewsFragment searchNewsFragment = new SearchNewsFragment();
        searchNewsFragment.setMNavigationItemId(R.id.leftnavigator_menu_home);
        Bundle args = new Bundle();
        args.putString(SEARCH_NAME, searchName);
        searchNewsFragment.setArguments(args);
        return searchNewsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFilterName = getArguments().getString(SEARCH_NAME);
        }
    }

    @Override
    public void getNewsList() {
        mNewsRepository.getNewsListBySearch(mFilterName, this);
    }
}
