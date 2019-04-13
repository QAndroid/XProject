package workshop1024.com.xproject.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.View;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.DisplaysettingsDialogBinding;

public class DisplaySettingsDialog extends DialogFragment {
    private float mSelectTextSize;

    private DialogFragment mDialogFragment;

    private DisplaySettingsDialogListener mDisplaySettingsDialogListener;

    private DisplaysettingsDialogBinding mDisplaysettingsDialogBinding;

    public void setSelectTextSize(float selectTextSize) {
        mSelectTextSize = selectTextSize;
    }

    public static DisplaySettingsDialog newInstance() {
        DisplaySettingsDialog displaySettingsDialog = new DisplaySettingsDialog();
        return displaySettingsDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDialogFragment = this;
        try {
            mDisplaySettingsDialogListener = (DisplaySettingsDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DisplaySettingsDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),
                R.style.xproject_alertdialog));
        mDisplaysettingsDialogBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.displaysettings_dialog,
                null, false);
        mDisplaysettingsDialogBinding.setDisplayHandlers(new DisplayHandlers());
        builder.setView(mDisplaysettingsDialogBinding.getRoot());

        mDisplaysettingsDialogBinding.displaysettingScalseekbarTextsize.setSelectedTextSize(mSelectTextSize);

        return builder.create();
    }

    public interface DisplaySettingsDialogListener {
        void onDisplaySettingDialogClick(DialogFragment dialogFragment, float textSize);
    }

    public class DisplayHandlers {
        public void onClickConfirm(View view) {
            mSelectTextSize = mDisplaysettingsDialogBinding.displaysettingScalseekbarTextsize.getSelectedTextSize();
            mDisplaySettingsDialogListener.onDisplaySettingDialogClick(mDialogFragment, mSelectTextSize);
            dismiss();
        }
    }
}
