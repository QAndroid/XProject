package workshop1024.com.xproject.view.preference

import android.content.Context
import android.preference.DialogPreference
import android.util.AttributeSet
import workshop1024.com.xproject.R

class ClearDataPreference(context: Context, attrs: AttributeSet) : DialogPreference(context, attrs) {

    init {
        setDialogTitle(R.string.settings_advance_cleardatadialog_title)
        setDialogMessage(R.string.settings_advance_cleardatadialog_message)
        setPositiveButtonText(R.string.settings_advance_cleardatadialog_positive)
        setNegativeButtonText(R.string.settings_advance_cleardatadialog_cancle)
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            //TODO 清除云端数据
        }
    }
}