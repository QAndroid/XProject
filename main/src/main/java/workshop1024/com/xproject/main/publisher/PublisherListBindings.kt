package workshop1024.com.xproject.main.publisher

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import workshop1024.com.xproject.main.publisher.data.Publisher

object PublisherListBindings {
    //参考：Databinding Binding adapters，https://developer.android.com/topic/libraries/data-binding/binding-adapters
    @BindingAdapter("app:publisherList")
    // 异常：Caused by: java.lang.IllegalStateException: Required DataBindingComponent is null in class
    // PublisherActivityBindingImpl. A BindingAdapter in workshop1024.com.xproject.main.publisher.
    // PublisherListBindings is not static and requires an object to use, retrieved from the
    // DataBindingComponent. If you don't use an inflation method taking a DataBindingComponent,
    // use DataBindingUtil.setDefaultComponent or make all BindingAdapter methods static.
    //解决：添加@JvmStatic注解
    @JvmStatic
    //异常：java.lang.IllegalArgumentException: Parameter specified as non-null is null: method kotlin
    // .jvm.internal.Intrinsics.checkParameterIsNotNull, parameter publisherList
    //
    fun setPublisherList(recyclerView: RecyclerView, publisherList: List<Publisher>?) {
        //width是一个单独的函数，并不是Kotlin的extension，所以调用方式不一样
        //参数this，返回最后一行
        //参考：https://www.jianshu.com/p/28ce69d58fea
        with(recyclerView.adapter as PublisherListAdapter) {
            publisherList?.let {
                refreshPublisherList(it)
            }
        }
    }
}