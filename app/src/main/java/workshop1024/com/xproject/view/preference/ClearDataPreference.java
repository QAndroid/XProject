package workshop1024.com.xproject.view.preference;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import workshop1024.com.xproject.R;

public class ClearDataPreference extends DialogPreference {

    public ClearDataPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogTitle(R.string.settings_advance_cleardatadialog_title);
        setDialogMessage(R.string.settings_advance_cleardatadialog_message);
        setPositiveButtonText(R.string.settings_advance_cleardatadialog_positive);
        setNegativeButtonText(R.string.settings_advance_cleardatadialog_cancle);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            //TODO 清除云端数据
        }
    }
}
