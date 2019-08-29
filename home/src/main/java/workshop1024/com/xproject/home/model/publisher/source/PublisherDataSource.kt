package workshop1024.com.xproject.home.model.publisher.source

import workshop1024.com.xproject.home.model.publisher.Publisher

/**
 * 发布者数据源接口，定义了关于发布者数据相关的处理接口
 */
interface PublisherDataSource {

    /**
     * 获取指定类型的发布者
     *
     * @param contentId                   发布者指定类型的id
     * @param loadPublishersCallback 加载发布者信息回调
     */
    fun getPublishersByContentType(contentId: String, loadPublishersCallback: LoadPublishersCallback)

    /**
     * 获取指定语言的发布者
     *
     * @param languageId               发布者指定的语言
     * @param loadPublishersCallback 加载发布者信息回调
     */
    fun getPublishersByLanguageType(languageId: String, loadPublishersCallback: LoadPublishersCallback)

    /**
     * 订阅指定id的发布者
     *
     * @param id 要订阅的发布者id
     */
    fun subscribePublisherById(id: String)

    /**
     * 取消订阅指定id发布者
     *
     * @param id 要取消订阅的发布者id
     */
    fun unSubscribePublisherById(id: String)

    /**
     * 获取发布者信息回调
     */
    interface LoadPublishersCallback {
        /**
         * 发布者信息加载完毕
         *
         * @param publisherList 加载返回的发布者信息
         */
        fun onPublishersLoaded(publisherList: List<Publisher>)

        /**
         * 没有有效的发布者信息
         */
        fun onDataNotAvailable()
    }
}
