package workshop1024.com.xproject.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import workshop1024.com.xproject.model.tag.Tag;

/**
 * 标题列表适配器
 */
public class TopicListAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * 标题列表表项接口
     */
    public interface TagListItemListener {
        /**
         * 标题列表项点击监听
         *
         * @param publisher 点击的标题名称
         */
        void onTopicListItemClick(Tag tag);
    }
}
