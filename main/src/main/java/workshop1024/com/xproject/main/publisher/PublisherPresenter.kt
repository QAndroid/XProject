package workshop1024.com.xproject.main.publisher

import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.PublisherType
import workshop1024.com.xproject.main.publisher.data.source.PublisherDataSource

/**
 * 需要处理数据等业务逻辑，在更新View的用户行为，则抽象到Presenter中；直接更新View不需要。
 */
class PublisherPresenter(private val mPublisherRepository: PublisherDataSource, private val mView: PublisherContract.View) :
        PublisherContract.Presenter, PublisherDataSource.LoadPublisherAndPublisherTypeCallback {

    override fun start() {
        mView.setLoadingIndicator(true)
        //开始请求发布者列表，IdlingResouce设置为false，等待异步执行完毕
        mView.setPublisherIdlingResouce(false)
        mPublisherRepository.getPublishersAndPublisherTypes(this)
    }

    override fun getPublishersByContentType(contentId: String) {
        mPublisherRepository.getPublishersByContentType(contentId, this)
    }

    override fun getPublishersByLanguageType(languageId: String) {
        mPublisherRepository.getPublishersByLanguageType(languageId, this)
    }

    override fun subscribePublisherBy(publisher: Publisher) {
        mPublisherRepository.subscribePublisherById(publisher.mPublisherId)
        mView.showSnackBar(publisher.mName + " selected")
    }

    override fun unSubscribePublisher(publisher: Publisher) {
        mPublisherRepository.unSubscribePublisherById(publisher.mPublisherId)
        mView.showSnackBar(publisher.mName + " unselected")
    }

    override fun onCacheOrLocalPublishersLoaded(publisherList: List<Publisher>) {
        mView.refreshPublisherList(publisherList)
        mView.showSnackBar("Fetch cacheorlocal " + publisherList.size + " publishers ...")
        if (!mPublisherRepository.getIsRequestRemote()) {
            mView.setLoadingIndicator(false)
        }
        mView.setPublisherIdlingResouce(true)
    }

    override fun onRemotePublishersLoaded(publisherList: List<Publisher>) {
        mView.refreshPublisherList(publisherList)
        mView.showSnackBar("Fetch remote " + publisherList.size + " publishers ...")
        mView.setLoadingIndicator(false)
        //请求发布者类表完毕，IdlingResouce设置为true，执行后面异步指令
        mView.setPublisherIdlingResouce(true)
    }


    override fun onCacheOrLocalPublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        mView.refreshPublisherTypeList(contentTypeList, languageTyleList)
    }

    override fun onRemotePublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        mView.refreshPublisherTypeList(contentTypeList, languageTyleList)
    }

    override fun onDataNotAvailable() {

    }
}

