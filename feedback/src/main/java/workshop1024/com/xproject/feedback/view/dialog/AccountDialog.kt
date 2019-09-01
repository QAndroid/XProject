package workshop1024.com.xproject.feedback.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import workshop1024.com.xproject.feedback.R
import workshop1024.com.xproject.feedback.databinding.AccountDialogBinding

class AccountDialog : DialogFragment(), DialogInterface.OnClickListener {
    private var mAccountDialogBinding: AccountDialogBinding? = null
    private var mAccountDialogListener: AccountDialogListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mAccountDialogListener = context as AccountDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement AccountDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mAccountDialogBinding = DataBindingUtil.inflate(activity!!.layoutInflater, R.layout.account_dialog, null, false)
        return AlertDialog.Builder(ContextThemeWrapper(activity,
                R.style.xproject_alertdialog)).setTitle(R.string.account_dialog_title).setView(mAccountDialogBinding?.root)
                .setPositiveButton(R.string.account_dialog_cancle, this).setNegativeButton(R.string.account_dialog_save, this)
                .create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            mAccountDialogListener?.cancelButtonClick(this)
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            mAccountDialogListener?.okButtonClick(this, mAccountDialogBinding!!.accountTextinputedittextName.text.toString(),
                    mAccountDialogBinding!!.accountTextinputedittextEmail.text.toString())
        }
    }

    interface AccountDialogListener {
        fun cancelButtonClick(dialogFragment: DialogFragment)
        fun okButtonClick(dialogFragment: DialogFragment, nameString: String, emailString: String)
    }

    companion object {
        const val TAG: String = "AccountDialog"

        fun newInstance(): AccountDialog {
            return AccountDialog()
        }
    }
}
