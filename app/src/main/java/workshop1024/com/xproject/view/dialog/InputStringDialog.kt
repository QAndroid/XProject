package workshop1024.com.xproject.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.view.ContextThemeWrapper
import workshop1024.com.xproject.R
import workshop1024.com.xproject.databinding.InputstringDialogBinding

/**
 * 输入字符串对话框
 */
class InputStringDialog : DialogFragment(), DialogInterface.OnClickListener {
    var mPreInputString: String? = null

    private var mTitleStringId: Int = 0
    private var mPositiveStringId: Int = 0

    var mInputStringDialogListener: InputStringDialogListener? = null
    private lateinit var mInputstringDialogBinding: InputstringDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mTitleStringId = it.getInt(TITLE_STRING_KEY)
            mPositiveStringId = it.getInt(POSITIVE_STRING_KEY)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mInputstringDialogBinding = DataBindingUtil.inflate(activity!!.layoutInflater, R.layout.inputstring_dialog, null, false)
        mInputstringDialogBinding.inputstringTextinputedittextName.setText(mPreInputString)

        return AlertDialog.Builder(ContextThemeWrapper(activity,
                R.style.xproject_alertdialog)).setTitle(mTitleStringId).setView(mInputstringDialogBinding.root).setPositiveButton(mPositiveStringId, this).create()
    }

    override fun onClick(dialogInterface: DialogInterface, i: Int) =
            mInputStringDialogListener!!.onInputStringDialogClick(this, mInputstringDialogBinding.inputstringTextinputedittextName.text.toString())


    /**
     * 输入字符串对话框点击监听接口
     */
    interface InputStringDialogListener {
        /**
         * 输入字符串确认点击回调方法
         *
         * @param dialog      点击的对话框
         * @param inputString 确认点击输入的字符串
         */
        fun onInputStringDialogClick(dialog: DialogFragment, inputString: String)
    }

    companion object {
        const val TITLE_STRING_KEY: String = "TitleString"
        const val POSITIVE_STRING_KEY: String = "PositiveString"

        /**
         * 创建输入字符串对话框实例
         *
         * @param titleStringId    标题字符串资源id
         * @param positiveStringId 确定按钮字符串资源id
         * @return 输入字符串对话框实例
         */
        fun newInstance(titleStringId: Int, positiveStringId: Int): InputStringDialog {
            val inputStringDialog = InputStringDialog()
            val bundle = Bundle()
            bundle.putInt(TITLE_STRING_KEY, titleStringId)
            bundle.putInt(POSITIVE_STRING_KEY, positiveStringId)
            inputStringDialog.arguments = bundle
            return inputStringDialog
        }
    }
}
