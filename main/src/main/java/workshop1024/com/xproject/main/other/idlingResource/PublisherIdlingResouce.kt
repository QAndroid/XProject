package workshop1024.com.xproject.main.other.idlingResource

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 发布页面展示前，异步请求发布者列表。
 * PublisherIdlingResouce用于等待异步列表请求完毕后，在执行相关的自动测试指令
 */
class PublisherIdlingResouce : IdlingResource {
    @Volatile
    private var mCallback: ResourceCallback? = null

    private val mIsIdleNow = AtomicBoolean(true)

    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return mIsIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback) {
        mCallback = callback
    }

    /**
     * 设置Resource的IdleState
     */
    fun setIdleState(isIdleNow: Boolean) {
        mIsIdleNow.set(isIdleNow)
        if (isIdleNow && mCallback != null) {
            mCallback?.onTransitionToIdle()
        }
    }
}