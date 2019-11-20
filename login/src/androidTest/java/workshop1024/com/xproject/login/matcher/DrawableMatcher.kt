package workshop1024.com.xproject.login.matcher

import android.widget.ImageView
import android.view.View
import androidx.annotation.DrawableRes
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

//自定义ImageView src匹配器
//参考：https://stackoverflow.com/questions/33763425/using-espresso-to-test-drawable-changes
class DrawableMatcher(@param:DrawableRes private val expectedId: Int) : TypeSafeMatcher<View>(View::class.java) {
    private var resourceName: String? = null

    override protected fun matchesSafely(target: View): Boolean {
        if (target !is ImageView) {
            return false
        }
        if (expectedId < 0) {
            return target.drawable == null
        }
        val resources = target.getContext().resources
        val expectedDrawable = resources.getDrawable(expectedId, null)
        resourceName = resources.getResourceEntryName(expectedId)
        return if (expectedDrawable != null && expectedDrawable.constantState != null) {
            expectedDrawable.constantState == target.drawable.constantState
        } else {
            false
        }
    }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(expectedId)
        if (resourceName != null) {
            description.appendText("[")
            description.appendText(resourceName)
            description.appendText("]")
        }
    }
}