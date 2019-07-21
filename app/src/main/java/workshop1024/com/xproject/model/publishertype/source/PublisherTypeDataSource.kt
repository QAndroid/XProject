package workshop1024.com.xproject.model.publishertype.source

import workshop1024.com.xproject.model.publishertype.PublisherType

interface PublisherTypeDataSource {
    /**
     * 获取发布者的所有内容类型
     *
     * @param loadPublisherTypeCallback 加载发布者内容类型信息回调
     */
    fun getPublisherContentTypes(loadPublisherTypeCallback: LoadPublisherTypeCallback)

    /**
     * 获取发布者的所有语言类型
     *
     * @param loadPublisherTypeCallback 加载发布者语言类型信息回调
     */
    fun getPublisherLanguageTypes(loadPublisherTypeCallback: LoadPublisherTypeCallback)

    /**
     * 获取发布者类型信息
     */
    interface LoadPublisherTypeCallback {
        /**
         * 发布者内容信息加载完毕
         *
         * @param publisherTypeList 加载返回的发布者类型信息
         */
        fun onPublisherTypesLoaded(publisherTypeList: List<PublisherType>, type: String)

        /**
         * 没有有效的发布者内容类型信息
         */
        fun onDataNotAvailable()
    }
}
