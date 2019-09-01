package workshop1024.com.xproject.home.controller.adapter

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import workshop1024.com.xproject.base.utils.UnitUtils
import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.model.Injection
import workshop1024.com.xproject.home.view.dialog.InputStringDialog

class TagListAdapter(private val mContext: Context, private val mTagList: List<String>) : RecyclerView.Adapter<TagListAdapter.TagViewHolder>() {

    private val mResource: Resources = mContext.resources

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val tagTextView = TextView(parent.context)
        tagTextView.setBackgroundResource(R.drawable.taglist_button_selector)
        tagTextView.setTextColor(mResource.getColor(R.color.newsdetail_taglist_textcolor, null))
        //TODO dp和像素的转换
        tagTextView.textSize = 14f
        tagTextView.setPadding(UnitUtils.dpToPx(mContext, 8f), UnitUtils.dpToPx(mContext, 5f),
                UnitUtils.dpToPx(mContext, 8f), UnitUtils.dpToPx(mContext, 5f))
        return TagViewHolder(tagTextView)
    }

    override fun getItemCount(): Int {
        return mTagList.size
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.mTagTextView.text = mTagList[position]
    }

    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, InputStringDialog.InputStringDialogListener {
        internal val mTagTextView: TextView = itemView as TextView

        init {
            mTagTextView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val addFilterDialog = InputStringDialog.newInstance(R.string.addfilter_dialog_title, R.string
                    .addfilter_dialog_positive)
            addFilterDialog.mPreInputString = mTagTextView.text.toString()
            addFilterDialog.mInputStringDialogListener = this
            //FIXME 似乎获取getSupportFragmentManager有点别扭
            addFilterDialog.show((mContext as AppCompatActivity).supportFragmentManager, "addFilterDialog")
        }

        override fun onInputStringDialogClick(dialog: DialogFragment, inputString: String) {
            val filterRepository = Injection.provideFilterRepository()
            filterRepository.addFilterByName(inputString)
        }

    }
}