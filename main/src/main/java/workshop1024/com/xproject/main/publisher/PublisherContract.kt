package workshop1024.com.xproject.main.publisher

import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.PublisherType

interface PublisherContract {
    interface View {
        fun setLoadingIndicator(isLoing: Boolean)

        fun refreshPublisherList(publisherList: List<Publisher>)

        fun showSnackBar(mMessage: String)

        fun setPublisherIdlingResouce(isIdling: Boolean)

        fun refreshPublisherTypeList(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>)
    }

    interface Presenter {
        fun start()

        fun getPublishersByContentType(contentId: String)

        fun getPublishersByLanguageType(languageId: String)

        fun subscribePublisherBy(publisher: Publisher)

        fun unSubscribePublisher(publisher: Publisher)
    }
}