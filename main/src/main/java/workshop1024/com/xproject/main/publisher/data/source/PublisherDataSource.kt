package workshop1024.com.xproject.main.publisher.data.source

import rx.Observable
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.PublisherType
import java.util.EnumMap

/**
 * 发布者数据源接口，定义了关于发布者数据相关的处理接口
 */
interface PublisherDataSource {
    /**
     * 因为远程和本地、缓存返回执行的逻辑不一样，故区分不同的被观察者类型返回
     */
    enum class PublisherInfoType {
        PUBLISHERS_REMOTE, PUBLISHERS_LOCAL_CACHE,
        CONTENT_TYPES_REMOTE, CONTENT_TYPES_LOCAL_CACHE,
        LANGUAGE_TYPES_REMOTE, LANGUAGE_TYPES_LOCAL_CACHE,
    }

    /**
     * 获取发布者和发布者类型信息
     * @return 原callback中返回类publisher和publishertype多种类型信息，故通过枚举map的形式返回多个结果
     */
    fun getPublishersAndPublisherTypes(): Observable<EnumMap<PublisherInfoType, Any>>

    /**
     * 获取指定类型的发布者
     *
     * @param contentId 发布者指定类型的id
     * @param LoadCallback 加载发布者信息回调
     */
    fun getPublishersByContentType(contentId: String): Observable<EnumMap<PublisherInfoType, Any>>

    /**
     * 获取指定语言的发布者
     *
     * @param languageId 发布者指定的语言
     * @param LoadCallback 加载发布者信息回调
     */
    fun getPublishersByLanguageType(languageId: String): Observable<EnumMap<PublisherInfoType, Any>>

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


    fun deleteAllPublishers()

    fun deleteAllPublisherTypes()

    fun savePublisher(publisher: Publisher)

    fun savePublisherType(publisherType: PublisherType)

    fun getIsRequestRemote(): Boolean
}
