package workshop1024.com.xproject.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.InputstringDialogBinding;

/**
 * 输入字符串对话框
 */
public class InputStringDialog extends DialogFragment implements DialogInterface.OnClickListener {
    public static final String TITLE_STRING_KEY = "TitleString";
    public static final String POSITIVE_STRING_KEY = "PositiveString";

    private TextInputEditText mEditText;
    private String mPreInputString;

    private int mTitleStringId;
    private int mPositiveStringId;

    private InputStringDialogListener mInputStringDialogListener;

    private InputstringDialogBinding mInputstringDialogBinding;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitleStringId = bundle.getInt(TITLE_STRING_KEY);
            mPositiveStringId = bundle.getInt(POSITIVE_STRING_KEY);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mInputstringDialogBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.inputstring_dialog,
                null, false);
        mInputstringDialogBinding.inputstringTextinputedittextName.setText(mPreInputString);

        return new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),
                R.style.xproject_alertdialog)).setTitle(mTitleStringId).setView(mInputstringDialogBinding.getRoot()).
                setPositiveButton(mPositiveStringId, this).create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        mInputStringDialogListener.onInputStringDialogClick(this, mInputstringDialogBinding.inputstringTextinputedittextName.
                getText().toString());
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
