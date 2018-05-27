package workshop1024.com.xproject.controller.fragment.home.news;

import android.os.Bundle;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.fragment.SubFragment;

public class SubscribeNewsFragment extends NewsListFragment implements SubFragment {
    private static final String SUBSCRIBE_NAME = "subscribe_Name";
    private String mSubscribeName;

    public static SubscribeNewsFragment newInstance(String subscribeName) {
        SubscribeNewsFragment subscribeNewsFragment = new SubscribeNewsFragment();
        subscribeNewsFragment.setNavigationItemId(R.id.leftnavigator_menu_home);
        Bundle args = new Bundle();
        args.putString(SUBSCRIBE_NAME, subscribeName);
        subscribeNewsFragment.setArguments(args);
        return subscribeNewsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSubscribeName = getArguments().getString(SUBSCRIBE_NAME);
        }
    }

    @Override
    public void getNewsList() {
        mNewsRepository.getNewsListBySubscribe(mSubscribeName, this);
    }
}
