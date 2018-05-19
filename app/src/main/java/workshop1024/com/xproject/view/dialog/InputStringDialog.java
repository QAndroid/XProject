package workshop1024.com.xproject.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import workshop1024.com.xproject.R;

/**
 * 输入字符串对话框
 */
public class InputStringDialog extends DialogFragment implements DialogInterface.OnClickListener {
    public static final String TITLE_STRING_KEY = "TitleString";
    public static final String POSITIVE_STRING_KEY = "PositiveString";
    private EditText mEditText;
    private String mPreInputString;
    private InputStringDialogListener mInputStringDialogListener;

    /**
     * 创建输入字符串对话框实例
     *
     * @param titleStringId    标题字符串资源id
     * @param positiveStringId 确定按钮字符串资源id
     * @return 输入字符串对话框实例
     */
    public static InputStringDialog newInstance(int titleStringId, int positiveStringId) {
        InputStringDialog inputStringDialog = new InputStringDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_STRING_KEY, titleStringId);
        bundle.putInt(POSITIVE_STRING_KEY, positiveStringId);
        inputStringDialog.setArguments(bundle);
        return inputStringDialog;
    }

    public void setInputStringDialogListener(InputStringDialogListener inputStringDialogListener) {
        mInputStringDialogListener = inputStringDialogListener;
    }

    public void setPreInputString(String preInputString) {
        mPreInputString = preInputString;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int titleStringId = bundle.getInt(TITLE_STRING_KEY);
        int positiveStringId = bundle.getInt(POSITIVE_STRING_KEY);

        FrameLayout frameLayout = new FrameLayout(getActivity());
        mEditText = new EditText(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = (int) getResources().getDimension(R.dimen.inputdialog_view_margin);
        layoutParams.setMargins(margin, 0, margin, 0);
        mEditText.setLayoutParams(layoutParams);
        if (mPreInputString != null) {
            mEditText.setText(mPreInputString);
        }
        frameLayout.addView(mEditText);

        return new AlertDialog.Builder(getActivity()).setTitle(titleStringId).setView(frameLayout).setPositiveButton
                (positiveStringId, this).create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        mInputStringDialogListener.onInputStringDialogClick(this, mEditText.getText().toString());
    }

    /**
     * 输入字符串对话框点击监听接口
     */
    public interface InputStringDialogListener {
        /**
         * 输入字符串确认点击回调方法
         *
         * @param dialog      点击的对话框
         * @param inputString 确认点击输入的字符串
         */
        void onInputStringDialogClick(DialogFragment dialog, String inputString);
    }
}
