package workshop1024.com.xproject.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import workshop1024.com.xproject.R;

public class AccountDialog extends DialogFragment implements DialogInterface.OnClickListener {
    public static final String TAG = "AccountDialog";

    private TextInputEditText mNameEditText;
    private TextInputEditText mEmailEditText;

    private AccountDialogListener mAccountDialogListener;

    public static AccountDialog newInstance() {
        AccountDialog accountDialog = new AccountDialog();
        return accountDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mAccountDialogListener = (AccountDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AccountDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View contentView = layoutInflater.inflate(R.layout.account_dialog, null);

        mNameEditText = contentView.findViewById(R.id.account_textinputedittext_name);
        mEmailEditText = contentView.findViewById(R.id.account_textinputedittext_email);

        return new AlertDialog.Builder(getActivity()).setTitle(R.string.account_dialog_title).setView(contentView).
                setPositiveButton(R.string.account_dialog_cancle, this).
                setNegativeButton(R.string.account_dialog_save, this).create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            mAccountDialogListener.cancelButtonClick(this);
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            mAccountDialogListener.okButtonClick(this, mNameEditText.getText().toString(),
                    mEmailEditText.getText().toString());
        }
    }

    public interface AccountDialogListener {
        void cancelButtonClick(DialogFragment dialogFragment);

        void okButtonClick(DialogFragment dialogFragment, String nameString, String emailString);
    }
}
