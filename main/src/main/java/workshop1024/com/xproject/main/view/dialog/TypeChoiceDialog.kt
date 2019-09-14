package workshop1024.com.xproject.main.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import workshop1024.com.xproject.main.R

import java.util.ArrayList

/**
 * 单选对话框
 */
class TypeChoiceDialog : DialogFragment(), DialogInterface.OnClickListener {

    //对话框标题
    private var mTitleString: String? = null
    //对话框选项
    private var mPublisherTypeList: ArrayList<workshop1024.com.xproject.main.model.publishertype.PublisherType>? = null
    //对话框选项索引
    private var mSelectedIndex: Int = 0

    private var mTypeChoiceDialogListener: TypeChoiceDialogListener? = null

    /**
     * 获取去选择的类型名称字符串集合
     *
     * @return 类型名称字符串集合
     */
    val typeNameStrings: Array<String?>
        get() {
            val nameStrings: Array<String?> = arrayOfNulls<String>(mPublisherTypeList!!.size)
            for (type_i in mPublisherTypeList!!.indices) {
                nameStrings[type_i] = mPublisherTypeList!![type_i].name
            }
            return nameStrings
        }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mTypeChoiceDialogListener = context as TypeChoiceDialogListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + " must implement TypeChoiceDialogListener")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            mTitleString = resources.getString(bundle.getInt(TITLE_STRING_KEY))
            mPublisherTypeList = bundle.getSerializable(SELECT_TYPE_KEY) as ArrayList<workshop1024.com.xproject.main.model.publishertype.PublisherType>?
            mSelectedIndex = bundle.getInt(SELECTED_INDEX_KEY)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(ContextThemeWrapper(activity,
                R.style.xproject_alertdialog))
        builder.setTitle(mTitleString).setItems(typeNameStrings, this)
        return builder.create()
    }

    override fun onClick(dialogInterface: DialogInterface, i: Int) {
        mSelectedIndex = i
        mTypeChoiceDialogListener!!.onTypeChoiceDialogItemClick(this, mPublisherTypeList!![mSelectedIndex])
    }

    /**
     * 类型选择对话框点击监听接口
     */
    interface TypeChoiceDialogListener {
        /**
         * 类型选择对话框选项点击回调方法
         *
         * @param dialog     点击的对话框
         * @param publisherType 点击的类型
         */
        fun onTypeChoiceDialogItemClick(dialog: DialogFragment, publisherType: workshop1024.com.xproject.main.model.publishertype.PublisherType)
    }

    companion object {
        const val TITLE_STRING_KEY = "TitleString"
        const val SELECT_TYPE_KEY = "SelectType"
        const val SELECTED_INDEX_KEY = "SelectedIndex"

        /**
         * 创建单选对话框实例
         *
         * @param selectStrings 选择字符串数组资源id
         * @param titleStringId 标题字符串资源id
         * @param singleItemList
         * @param selectedIndex 选择的字符串索引
         * @return 单选对话框实例
         */
        fun newInstance(titleStringId: Int, publisherTypes: ArrayList<workshop1024.com.xproject.main.model.publishertype.PublisherType>, selectedIndex: Int): TypeChoiceDialog {
            val typeChoiceDialog = TypeChoiceDialog()
            val bundle = Bundle()
            bundle.putInt(TITLE_STRING_KEY, titleStringId)
            bundle.putSerializable(SELECT_TYPE_KEY, publisherTypes)
            bundle.putInt(SELECTED_INDEX_KEY, selectedIndex)
            typeChoiceDialog.arguments = bundle
            return typeChoiceDialog
        }
    }
}
