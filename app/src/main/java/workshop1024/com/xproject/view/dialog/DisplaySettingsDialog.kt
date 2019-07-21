package workshop1024.com.xproject.view.dialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.view.ContextThemeWrapper
import android.view.View
import workshop1024.com.xproject.R
import workshop1024.com.xproject.databinding.DisplaysettingsDialogBinding

class DisplaySettingsDialog : DialogFragment() {
    var mSelectTextSize: Float = 0.0f
    private lateinit var mDialogFragment: DialogFragment
    private var mDisplaySettingsDialogListener: DisplaySettingsDialogListener? = null
    private lateinit var mDisplaysettingsDialogBinding: DisplaysettingsDialogBinding

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mDialogFragment = this
        try {
            mDisplaySettingsDialogListener = context as DisplaySettingsDialogListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + " must implement DisplaySettingsDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(ContextThemeWrapper(activity,
                R.style.xproject_alertdialog))
        mDisplaysettingsDialogBinding = DataBindingUtil.inflate(activity!!.layoutInflater, R.layout.displaysettings_dialog,
                null, false)
        mDisplaysettingsDialogBinding.displayHandlers = DisplayHandlers()
        builder.setView(mDisplaysettingsDialogBinding.root)

        mDisplaysettingsDialogBinding.displaysettingScalseekbarTextsize.selectedTextSize = mSelectTextSize

        return builder.create()
    }

    interface DisplaySettingsDialogListener {
        fun onDisplaySettingDialogClick(dialogFragment: DialogFragment, textSize: Float)
    }

    inner class DisplayHandlers {
        fun onClickConfirm(view: View) {
            mSelectTextSize = mDisplaysettingsDialogBinding.displaysettingScalseekbarTextsize.selectedTextSize
            mDisplaySettingsDialogListener?.onDisplaySettingDialogClick(mDialogFragment, mSelectTextSize)
            dismiss()
        }
    }

    companion object {
        fun newInstance(): DisplaySettingsDialog {
            return DisplaySettingsDialog()
        }
    }
}
