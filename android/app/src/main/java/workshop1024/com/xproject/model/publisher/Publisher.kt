package workshop1024.com.xproject.model.publisher

import android.databinding.ObservableBoolean

/**
 * 发布者数据类
 */
class Publisher( //发布者id
        var publisherId: String, //发布者类型
        var typeId: String,//发布者语言类型
        var language: String,//发布者图标URL
        var iconUrl: String,//发布者名称
        var name: String,//发布者订阅数量
        var subscribeNum: String,//发布者是否被订阅
        isSubscribed: Boolean
) {
    var isSubscribed: ObservableBoolean = ObservableBoolean()

    init {
        this.isSubscribed.set(isSubscribed)
    }
}