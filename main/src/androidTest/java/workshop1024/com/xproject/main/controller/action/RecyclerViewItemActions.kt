package workshop1024.com.xproject.main.controller.action

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController

import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

/**
 * RecyclerView Item相关的Action
 */
//操作RecyclerViewItem内的元素，并检测
//参考：https://medium.com/@xabaras/dealing-with-actions-and-checks-on-children-of-a-recyclerviews-item-in-espresso-tests-dabd93361810
class RecyclerViewItemActions {
    companion object {
        /**
         * 在RecyclerView的Item中，在指定id的View上执行一个action
         */
        fun actionByChildId(action: ViewAction, childId: Int): ViewAction {
            return object : ViewAction {
                override fun getDescription(): String {
                    //用不描述这个view Action
                    return "Perform action on the view whose id is passed in"
                }

                override fun getConstraints(): Matcher<View> {
                    //指定什么类型的View可以执行这个Action的机制
                    //显示的View可以执行
                    return allOf(isDisplayed(), isAssignableFrom(View::class.java))
                }

                override fun perform(uiController: UiController?, view: View?) {
                    //在指定的View上执行action
                    view?.let {
                        val child: View = it.findViewById(childId)
                        action.perform(uiController, child)
                    }
                }

            }
        }

        /**
         * 在RecyclerView的item（指定它的postion），检查指定id的视图撇配childMatcher
         */
        fun childViewByPositionWithMatcher(childId: Int, position: Int, childMatcher: Matcher<View>): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("Checks that the matcher childMatcher matches" +
                            " with a view having a given id inside a RecyclerView's item (given its position)")
                }

                override fun matchesSafely(recyclerView: RecyclerView?): Boolean {
                    //获取RecyclerView指定position的viewHolder
                    val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position)
                    //创建子视图满足指定id和指定childMatcher的matcher
                    val matcher = hasDescendant(allOf(withId(childId), childMatcher))
                    //匹配器检查指定postion的itemview
                    return viewHolder != null && matcher.matches(viewHolder.itemView)
                }

            }
        }
    }

}