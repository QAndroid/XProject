package workshop1024.com.xproject.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.AccountDialogBinding;

public class AccountDialog extends DialogFragment implements DialogInterface.OnClickListener {
    public static final String TAG = "AccountDialog";

    private AccountDialogBinding mAccountDialogBinding;
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
        mAccountDialogBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.account_dialog, null, false);
        return new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),
                R.style.xproject_alertdialog)).setTitle(R.string.account_dialog_title).setView(mAccountDialogBinding.getRoot()).
                setPositiveButton(R.string.account_dialog_cancle, this).
                setNegativeButton(R.string.account_dialog_save, this).create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            mAccountDialogListener.cancelButtonClick(this);
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            mAccountDialogListener.okButtonClick(this, mAccountDialogBinding.accountTextinputedittextName.getText().toString(),
                    mAccountDialogBinding.accountTextinputedittextEmail.getText().toString());
        }
    }

    public interface AccountDialogListener {
        void cancelButtonClick(DialogFragment dialogFragment);

        void okButtonClick(DialogFragment dialogFragment, String nameString, String emailString);
    }
}
