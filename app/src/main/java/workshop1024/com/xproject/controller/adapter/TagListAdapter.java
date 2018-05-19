package workshop1024.com.xproject.controller.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.utils.UnitUtils;

public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.TagViewHolder> {
    private Context mContext;
    private Resources mResource;
    private List<String> mTagList;

    public TagListAdapter(Context context, List<String> tagList) {
        mContext = context;
        mTagList = tagList;
        mResource = context.getResources();
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView tagTextView = new TextView(parent.getContext());
        tagTextView.setBackgroundResource(R.drawable.taglist_button_selector);
        tagTextView.setTextColor(mResource.getColor(R.color.white, null));
        //TODO dp和像素的转换
        tagTextView.setTextSize(14);
        tagTextView.setPadding(UnitUtils.dip2px(mContext, 8), UnitUtils.dip2px(mContext, 5),
                UnitUtils.dip2px(mContext, 8), UnitUtils.dip2px(mContext, 5));
        return new TagViewHolder(tagTextView);
    }

    @Override
    public void onBindViewHolder(TagViewHolder holder, int position) {
        holder.mTagTextView.setText(mTagList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTagList.size();
    }

    public class TagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTagTextView;

        public TagViewHolder(View itemView) {
            super(itemView);
            mTagTextView = (TextView) itemView;
            mTagTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "TagClick", Toast.LENGTH_SHORT).show();
        }
    }
}
