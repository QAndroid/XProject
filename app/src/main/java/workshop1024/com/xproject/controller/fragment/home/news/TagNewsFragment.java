package workshop1024.com.xproject.controller.fragment.home.news;

import android.os.Bundle;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.fragment.SubFragment;

public class TagNewsFragment extends NewsListFragment implements SubFragment {
    private static final String TAG_NAME = "tag_Name";
    private String mTagName;

    public static TagNewsFragment newInstance(String subscribeName) {
        TagNewsFragment tagNewsFragment = new TagNewsFragment();
        tagNewsFragment.setNavigationItemId(R.id.leftnavigator_menu_home);
        Bundle args = new Bundle();
        args.putString(TAG_NAME, subscribeName);
        tagNewsFragment.setArguments(args);
        return tagNewsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTagName = getArguments().getString(TAG_NAME);
        }
    }

    @Override
    public void getNewsList() {
        mNewsRepository.getNewsListByTag(mTagName, this);
    }
}
