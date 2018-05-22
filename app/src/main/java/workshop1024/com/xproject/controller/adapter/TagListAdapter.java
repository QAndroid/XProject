package workshop1024.com.xproject.controller.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.filter.source.FilterRepository;
import workshop1024.com.xproject.utils.UnitUtils;
import workshop1024.com.xproject.view.dialog.InputStringDialog;

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
        tagTextView.setPadding(UnitUtils.dpToPx(mContext, 8), UnitUtils.dpToPx(mContext, 5),
                UnitUtils.dpToPx(mContext, 8), UnitUtils.dpToPx(mContext, 5));
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

    public class TagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            InputStringDialog.InputStringDialogListener {
        private TextView mTagTextView;

        public TagViewHolder(View itemView) {
            super(itemView);
            mTagTextView = (TextView) itemView;
            mTagTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            InputStringDialog addFilterDialog = InputStringDialog.newInstance(R.string.addfilter_dialog_title, R.string
                    .addfilter_dialog_positive);
            addFilterDialog.setPreInputString(mTagTextView.getText().toString());
            addFilterDialog.setInputStringDialogListener(this);
            //FIXME 似乎获取getSupportFragmentManager有点别扭
            addFilterDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "addFilterDialog");
        }

        @Override
        public void onInputStringDialogClick(DialogFragment dialog, String inputString) {
            FilterRepository filterRepository = FilterRepository.getInstance();
            filterRepository.addFilterByName(inputString);
        }
    }
}
