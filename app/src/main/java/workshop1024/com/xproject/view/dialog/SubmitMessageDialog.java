package workshop1024.com.xproject.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.SubmitmessageDialogBinding;

public class SubmitMessageDialog extends DialogFragment {
    public static final String TAG = "SubmitMessageDialog";
    private SubmitMessageDialog mSubmitMessageDialog;

    private SubmitMessageDialogListener mSubmitMessageDialogListener;
    private SubmitmessageDialogBinding mSubmitmessageDialogBinding;

    public static SubmitMessageDialog newInstance() {
        SubmitMessageDialog submitMessageDialog = new SubmitMessageDialog();
        return submitMessageDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSubmitMessageDialog = this;
        try {
            mSubmitMessageDialogListener = (SubmitMessageDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SubmitMessageDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSubmitmessageDialogBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(),
                R.layout.submitmessage_dialog, null, false);
        mSubmitmessageDialogBinding.setSubmitMessageHandlers(new SubmitMessageHandlers());
        return new AlertDialog.Builder(getActivity()).setView(mSubmitmessageDialogBinding.getRoot()).create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSubmitMessageDialogListener.cancelButtonClick(this);
    }

    public interface SubmitMessageDialogListener {
        void cancelButtonClick(DialogFragment dialogFragment);

        void submitButtonClick(DialogFragment dialogFragment, String messageConent);
    }

    public class SubmitMessageHandlers {
        public void onClickCancel(View view) {
            mSubmitMessageDialogListener.cancelButtonClick(mSubmitMessageDialog);
        }

        public void onClickSubmit(View view) {
            mSubmitMessageDialogListener.submitButtonClick(mSubmitMessageDialog, mSubmitmessageDialogBinding.
                    submitmessageEdittextMessage.getText().toString());
        }
    }
}