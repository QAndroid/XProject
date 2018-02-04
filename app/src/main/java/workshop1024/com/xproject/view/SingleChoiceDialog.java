package workshop1024.com.xproject.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * 单选对话框
 */
public class SingleChoiceDialog extends DialogFragment implements DialogInterface.OnClickListener {
    public static final String TITLE_STRING_KEY = "TitleString";
    public static final String SELECT_STRINGS_KEY = "SelectStrings";
    public static final String SELECTED_INDEX_KEY = "SelectedIndex";

    //对话框标题
    private String mTitleString;
    //对话框选项
    private String[] mSelectStrings;
    //对话框选项索引
    private int mSelectedIndex;

    private SingleChoiceDialogListener mSingleChoiceDialogListener;

    /**
     * 创建对话框实例
     *
     * @param titleStringId 标题字符串资源id
     * @param selectStrings 选择字符串数组资源id
     * @param selectedIndex 选择的字符串索引
     * @return 对话框实例
     */
    public static SingleChoiceDialog newInstance(int titleStringId, String[] selectStrings, int selectedIndex) {
        SingleChoiceDialog singleChoiceDialog = new SingleChoiceDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_STRING_KEY, titleStringId);
        bundle.putStringArray(SELECT_STRINGS_KEY, selectStrings);
        bundle.putInt(SELECTED_INDEX_KEY, selectedIndex);
        singleChoiceDialog.setArguments(bundle);
        return singleChoiceDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mSingleChoiceDialogListener = (SingleChoiceDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SingleChoiceDialogListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitleString = getResources().getString(bundle.getInt(TITLE_STRING_KEY));
            mSelectStrings = bundle.getStringArray(SELECT_STRINGS_KEY);
            mSelectedIndex = bundle.getInt(SELECTED_INDEX_KEY);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mTitleString).setItems(mSelectStrings, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        mSelectedIndex = i;
        mSingleChoiceDialogListener.onSingleChoiceDialogItemClick(this, mSelectStrings[mSelectedIndex]);
    }

    /**
     * 获取选择的字符串
     *
     * @return 选择的字符串
     */
    public String getSelectedString() {
        return mSelectStrings[mSelectedIndex];
    }

    /**
     * 单选对话框点击监听接口
     */
    public interface SingleChoiceDialogListener {
        /**
         * 单选对话框选项点击回调方法
         *
         * @param dialog     点击的对话框
         * @param selectItem 点击的选项字符串
         */
        void onSingleChoiceDialogItemClick(DialogFragment dialog, String selectItem);
    }
}
