package workshop1024.com.xproject.view.dialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import workshop1024.com.xproject.R
import workshop1024.com.xproject.databinding.SubmitmessageDialogBinding

class SubmitMessageDialog : DialogFragment() {
    private lateinit var mSubmitMessageDialog: SubmitMessageDialog

    private var mSubmitMessageDialogListener: SubmitMessageDialogListener? = null
    private lateinit var mSubmitmessageDialogBinding: SubmitmessageDialogBinding

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mSubmitMessageDialog = this
        try {
            mSubmitMessageDialogListener = context as SubmitMessageDialogListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + " must implement SubmitMessageDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mSubmitmessageDialogBinding = DataBindingUtil.inflate(activity!!.layoutInflater,
                R.layout.submitmessage_dialog, null, false)
        mSubmitmessageDialogBinding.submitMessageHandlers = SubmitMessageHandlers()
        return AlertDialog.Builder(activity!!).setView(mSubmitmessageDialogBinding.root).create()
    }

    override fun onDetach() {
        super.onDetach()
        mSubmitMessageDialogListener!!.cancelButtonClick(this)
    }


    interface SubmitMessageDialogListener {
        fun cancelButtonClick(dialogFragment: DialogFragment)

        fun submitButtonClick(dialogFragment: DialogFragment, messageConent: String)
    }

    inner class SubmitMessageHandlers {
        fun onClickCancel(view: View) {
            mSubmitMessageDialogListener!!.cancelButtonClick(mSubmitMessageDialog)
        }

        fun onClickSubmit(view: View) {
            mSubmitMessageDialogListener!!.submitButtonClick(mSubmitMessageDialog, mSubmitmessageDialogBinding.submitmessageEdittextMessage.text.toString())
        }
    }

    companion object {
        const val TAG = "SubmitMessageDialog"

        fun newInstance(): SubmitMessageDialog {
            return SubmitMessageDialog()
        }

    }
}