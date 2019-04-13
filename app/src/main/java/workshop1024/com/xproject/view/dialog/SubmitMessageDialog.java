package workshop1024.com.xproject.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import workshop1024.com.xproject.R;

public class SubmitMessageDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "SubmitMessageDialog";

    private ImageButton mCancleButton;
    private ImageButton mSubmitButton;
    private EditText mMessageEditText;

    private SubmitMessageDialogListener mSubmitMessageDialogListener;

    public static SubmitMessageDialog newInstance() {
        SubmitMessageDialog submitMessageDialog = new SubmitMessageDialog();
        return submitMessageDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mSubmitMessageDialogListener = (SubmitMessageDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SubmitMessageDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View contentView = layoutInflater.inflate(R.layout.submitmessage_dialog, null);
        mCancleButton = contentView.findViewById(R.id.submitmessage_imagebutton_cancel);
        mSubmitButton = contentView.findViewById(R.id.submitmessage_imagebutton_submit);
        mMessageEditText = contentView.findViewById(R.id.submitmessage_edittext_message);

        mCancleButton.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);

        return new AlertDialog.Builder(getActivity()).setView(contentView).create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSubmitMessageDialogListener.cancelButtonClick(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mCancleButton) {
            mSubmitMessageDialogListener.cancelButtonClick(this);
        } else if (v == mSubmitButton) {
            mSubmitMessageDialogListener.submitButtonClick(this, mMessageEditText.getText().toString());
        }
    }

    public interface SubmitMessageDialogListener {
        void cancelButtonClick(DialogFragment dialogFragment);

        void submitButtonClick(DialogFragment dialogFragment, String messageConent);
    }
}