package workshop1024.com.xproject.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;

import java.util.ArrayList;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.publishertype.PublisherType;

/**
 * 单选对话框
 */
public class TypeChoiceDialog extends DialogFragment implements DialogInterface.OnClickListener {
    public static final String TITLE_STRING_KEY = "TitleString";
    public static final String SELECT_TYPE_KEY = "SelectType";
    public static final String SELECTED_INDEX_KEY = "SelectedIndex";

    //对话框标题
    private String mTitleString;
    //对话框选项
    private ArrayList<PublisherType> mPublisherTypeList;
    //对话框选项索引
    private int mSelectedIndex;

    private TypeChoiceDialogListener mTypeChoiceDialogListener;

    public TypeChoiceDialog() {
    }

    /**
     * 创建单选对话框实例
     *
     * @param selectStrings 选择字符串数组资源id
     * @param titleStringId 标题字符串资源id
     * @param singleItemList
     * @param selectedIndex 选择的字符串索引
     * @return 单选对话框实例
     */
    public static TypeChoiceDialog newInstance(int titleStringId, ArrayList<PublisherType> publisherTypes, int selectedIndex) {
        TypeChoiceDialog typeChoiceDialog = new TypeChoiceDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_STRING_KEY, titleStringId);
        bundle.putSerializable(SELECT_TYPE_KEY, publisherTypes);
        bundle.putInt(SELECTED_INDEX_KEY, selectedIndex);
        typeChoiceDialog.setArguments(bundle);
        return typeChoiceDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mTypeChoiceDialogListener = (TypeChoiceDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TypeChoiceDialogListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitleString = getResources().getString(bundle.getInt(TITLE_STRING_KEY));
            mPublisherTypeList = (ArrayList<PublisherType>) bundle.getSerializable(SELECT_TYPE_KEY);
            mSelectedIndex = bundle.getInt(SELECTED_INDEX_KEY);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),
                R.style.xproject_alertdialog));
        builder.setTitle(mTitleString).setItems(getTypeNameStrings(), this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        mSelectedIndex = i;
        mTypeChoiceDialogListener.onTypeChoiceDialogItemClick(this, mPublisherTypeList.get(mSelectedIndex));
    }

    /**
     * 获取去选择的类型名称字符串集合
     *
     * @return 类型名称字符串集合
     */
    public String[] getTypeNameStrings() {
        String[] nameStrings = new String[mPublisherTypeList.size()];
        for (int type_i = 0; type_i < mPublisherTypeList.size(); type_i++) {
            nameStrings[type_i] = mPublisherTypeList.get(type_i).getName();
        }
        return nameStrings;
    }

    /**
     * 类型选择对话框点击监听接口
     */
    public interface TypeChoiceDialogListener {
        /**
         * 类型选择对话框选项点击回调方法
         *
         * @param dialog     点击的对话框
         * @param publisherType 点击的类型
         */
        void onTypeChoiceDialogItemClick(DialogFragment dialog, PublisherType publisherType);
    }
}
