package workshop1024.com.xproject.feedback.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import workshop1024.com.xproject.feedback.R
import workshop1024.com.xproject.feedback.databinding.SubmitmessageDialogBinding

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