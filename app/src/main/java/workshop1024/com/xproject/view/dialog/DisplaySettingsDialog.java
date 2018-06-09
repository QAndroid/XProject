package workshop1024.com.xproject.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.view.ScaleSeekBar;

public class DisplaySettingsDialog extends DialogFragment implements View.OnClickListener {
    private ScaleSeekBar mScaleSeekBar;
    private Button mConfirmButton;

    private int mSelectTextSize;

    private DisplaySettingsDialogListener mDisplaySettingsDialogListener;

    public static DisplaySettingsDialog newInstance() {
        DisplaySettingsDialog displaySettingsDialog = new DisplaySettingsDialog();
        return displaySettingsDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mDisplaySettingsDialogListener = (DisplaySettingsDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DisplaySettingsDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View contentView = layoutInflater.inflate(R.layout.displaysettings_dialog, null);
        builder.setView(contentView);

        mScaleSeekBar = contentView.findViewById(R.id.displaysetting_scalseekbar_textsize);
        mConfirmButton = contentView.findViewById(R.id.displaysetting_button_confirm);
        mScaleSeekBar.setSelectedTextSize(mSelectTextSize);
        mConfirmButton.setOnClickListener(this);

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        mSelectTextSize = mScaleSeekBar.getSelectedTextSize();
        mDisplaySettingsDialogListener.onDisplaySettingDialogClick(this, mSelectTextSize);
        dismiss();
    }


    public interface DisplaySettingsDialogListener {
        void onDisplaySettingDialogClick(DialogFragment dialogFragment, int textSize);
    }
}
