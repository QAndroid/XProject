package workshop1024.com.xproject.main.publisher

/**
 * 用于作为一个数据包装器，期望通过LiveData表示事件
 * 在Config改变的时候，LiveData观察者会再次回调，用于处理已经处理过的数据，如Snack
 */
open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set //允许外部读不允许写

    /**
     * 返回内容并防止再次使用
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * 返回内容，即使它已经被使用
     */
    fun peekContent(): T = content
}