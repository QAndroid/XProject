package workshop1024.com.xproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.fragment.dummy.DummyContent;
import workshop1024.com.xproject.fragment.dummy.DummyContent.DummyItem;

/**
 * 故事Fragment
 */
public class StoryFragment extends SubFragment {
    private RecyclerView mStoryRecyclerView;
    private OnListFragmentInteractionListener mListener;

    public StoryFragment() {
    }

    public static StoryFragment newInstance() {
        StoryFragment fragment = new StoryFragment();
        fragment.setNavigationItemId(R.id.leftnavigator_menu_home);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storylist_fragment, container, false);

        mStoryRecyclerView = view.findViewById(R.id.story_recyclerview_list);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mStoryRecyclerView.setAdapter(new MyStoryRecyclerViewAdapter(DummyContent.ITEMS, mListener));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }

    public class MyStoryRecyclerViewAdapter extends RecyclerView.Adapter<MyStoryRecyclerViewAdapter.ViewHolder> {

        private final List<DummyItem> mValues;
        private final OnListFragmentInteractionListener mListener;

        public MyStoryRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
            mValues = items;
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
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onListFragmentInteraction(holder.mItem);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.storylist_textview_id);
                mContentView = (TextView) view.findViewById(R.id.storylist_textview_content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

}
