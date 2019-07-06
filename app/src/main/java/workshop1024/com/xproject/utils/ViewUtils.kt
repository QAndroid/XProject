package workshop1024.com.xproject.utils

import android.view.View

object ViewUtils {
    fun getRelativeLeft(view: View): Int {
        return if (view.parent === view.rootView) {
            view.left
        } else {
            view.left + getRelativeLeft(view.parent as View)
        }
    }

    fun getRelativeTop(view: View): Int {
        return if (view.parent === view.rootView) {
            view.top
        } else {
            view.top + getRelativeTop(view.parent as View)
        }
    }
}