package workshop1024.com.xproject.controller.fragment;

import android.content.Context;
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
import workshop1024.com.xproject.model.Story;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;

/**
 * 故事Fragment
 */
public class ListFragment extends SubFragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    private RecyclerView mStoryRecyclerView;

    private StoryRecyclerViewAdapter mStoryRecyclerViewAdapter;

    private OnStoryListItemClickListener mListener;

    private List<Story> mStoryList = new ArrayList<Story>() {{
        add(new Story("/imag1", "title1title1title1title1title1title1title1", "author1", "time1"));
        add(new Story("/imag2", "title2title2title2title2title2title2title2", "author2", "time2"));
        add(new Story("/imag3", "title3title3title3title3title3title3title3", "author3", "time3"));
        add(new Story("/imag4", "title4title4title4title4title4title4title4", "author4", "time4"));
    }};

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        fragment.setNavigationItemId(R.id.leftnavigator_menu_home);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        mSwipeRefreshLayoutPull = view.findViewById(R.id.stroy_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mStoryRecyclerView = view.findViewById(R.id.story_recyclerview_list);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mStoryRecyclerViewAdapter = new StoryRecyclerViewAdapter(mStoryList, mListener);
        mStoryRecyclerView.setAdapter(mStoryRecyclerViewAdapter);
        mStoryRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(16));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStoryListItemClickListener) {
            mListener = (OnStoryListItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnStoryListItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        mStoryList.add(new Story("/imagen1", "titlen1", "authorn1", "timen1"));
        mStoryList.add(new Story("/imagen2", "titlen2", "authorn2", "timen2"));

        mStoryRecyclerViewAdapter.notifyDataSetChanged();
        mSwipeRefreshLayoutPull.setRefreshing(false);
    }

    public interface OnStoryListItemClickListener {
        void onListFragmentInteraction(Story story);
    }

    public class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryRecyclerViewAdapter.ViewHolder> {
        private List<Story> mStoryList;
        private OnStoryListItemClickListener mListener;

        public StoryRecyclerViewAdapter(List<Story> storyList, OnStoryListItemClickListener listener) {
            mStoryList = storyList;
            mListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storylist_item_content, parent,
                    false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mStory = mStoryList.get(position);

            holder.mTitleTextView.setText(mStoryList.get(position).getTitle());
            holder.mAuthorTextView.setText(mStoryList.get(position).getAuthor());
            holder.mTimeTextView.setText(mStoryList.get(position).getTime());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onListFragmentInteraction(holder.mStory);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mStoryList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final ImageView mBannerImageView;
            public final TextView mTitleTextView;
            public final TextView mAuthorTextView;
            public final TextView mTimeTextView;

            private Story mStory;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mBannerImageView = view.findViewById(R.id.storylist_imageview_banner);
                mTitleTextView = view.findViewById(R.id.storylist_textview_title);
                mAuthorTextView = view.findViewById(R.id.storylist_textview_author);
                mTimeTextView = view.findViewById(R.id.storylist_textview_time);
            }
        }
    }

}
