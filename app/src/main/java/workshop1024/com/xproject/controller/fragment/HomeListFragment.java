package workshop1024.com.xproject.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.DetailActivity;
import workshop1024.com.xproject.model.Story;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;

/**
 * 抽屉导航Home Fragment的PageFragment列表选项点击后展示的子列表Fragment
 */
public class HomeListFragment extends SubFragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    private RecyclerView mStoryRecyclerView;

    private StoryRecyclerViewAdapter mStoryRecyclerViewAdapter;

    private List<Story> mStoryList = new ArrayList<Story>() {{
        add(new Story("/imag1", "title1title1title1title1title1title1title1", "author1", "time1"));
        add(new Story("/imag2", "title2title2title2title2title2title2title2", "author2", "time2"));
        add(new Story("/imag3", "title3title3title3title3title3title3title3", "author3", "time3"));
        add(new Story("/imag4", "title4title4title4title4title4title4title4", "author4", "time4"));
    }};

    public HomeListFragment() {
    }

    public static HomeListFragment newInstance() {
        HomeListFragment fragment = new HomeListFragment();
        fragment.setNavigationItemId(R.id.leftnavigator_menu_home);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homelist_fragment, container, false);

        mSwipeRefreshLayoutPull = view.findViewById(R.id.stroy_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mStoryRecyclerView = view.findViewById(R.id.story_recyclerview_list);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mStoryRecyclerViewAdapter = new StoryRecyclerViewAdapter(mStoryList);
        mStoryRecyclerView.setAdapter(mStoryRecyclerViewAdapter);
        mStoryRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));

        return view;
    }

    @Override
    public void onRefresh() {
        mStoryList.add(new Story("/imagen1", "titlen1", "authorn1", "timen1"));
        mStoryList.add(new Story("/imagen2", "titlen2", "authorn2", "timen2"));

        mStoryRecyclerViewAdapter.notifyDataSetChanged();
        mSwipeRefreshLayoutPull.setRefreshing(false);
    }

    public class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryRecyclerViewAdapter.StoryViewHolder> {
        private List<Story> mStoryList;

        public StoryRecyclerViewAdapter(List<Story> storyList) {
            mStoryList = storyList;
        }

        @Override
        public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storylist_item_content, parent,
                    false);
            return new StoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final StoryViewHolder holder, int position) {
            holder.mTitleTextView.setText(mStoryList.get(position).getTitle());
            holder.mAuthorTextView.setText(mStoryList.get(position).getAuthor());
            holder.mTimeTextView.setText(mStoryList.get(position).getTime());
        }

        @Override
        public int getItemCount() {
            return mStoryList.size();
        }

        public class StoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public final ImageView mBannerImageView;
            public final TextView mTitleTextView;
            public final TextView mAuthorTextView;
            public final TextView mTimeTextView;

            public StoryViewHolder(View view) {
                super(view);
                view.setOnClickListener(this);

                mBannerImageView = view.findViewById(R.id.storylist_imageview_banner);
                mTitleTextView = view.findViewById(R.id.storylist_textview_title);
                mAuthorTextView = view.findViewById(R.id.storylist_textview_author);
                mTimeTextView = view.findViewById(R.id.storylist_textview_time);
            }

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                startActivity(intent);
            }
        }
    }

}
