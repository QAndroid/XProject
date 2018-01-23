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
public class SingleChoiceDialog extends DialogFragment {
    public static final String TITLE_STRING_KEY = "TitleString";
    public static final String CHOICE_STRINGS_KEY = "ChoiceStrings";

    //对话框标题
    private String mTitleString;
    //对话框选项
    private String[] mChoiceStrings;

    private DialogInterface.OnClickListener mOnItemClickListener;

    public static SingleChoiceDialog newInstance(int titleStringId, int choiceStringsId) {
        SingleChoiceDialog singleChoiceDialog = new SingleChoiceDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_STRING_KEY, titleStringId);
        bundle.putInt(CHOICE_STRINGS_KEY, choiceStringsId);
        singleChoiceDialog.setArguments(bundle);
        return singleChoiceDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnItemClickListener = (DialogInterface.OnClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitleString = getResources().getString(bundle.getInt(TITLE_STRING_KEY));
            mChoiceStrings = getResources().getStringArray(bundle.getInt(CHOICE_STRINGS_KEY));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mTitleString).setItems(mChoiceStrings, mOnItemClickListener);
        return builder.create();
    }
}
