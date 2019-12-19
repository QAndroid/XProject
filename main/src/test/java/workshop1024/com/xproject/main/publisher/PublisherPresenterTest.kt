package workshop1024.com.xproject.main.publisher

import android.util.Log
import androidx.databinding.ObservableBoolean
import com.nhaarman.mockitokotlin2.argumentCaptor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.source.PublisherDataSource
import workshop1024.com.xproject.main.publisher.data.source.PublisherRepository

/**
 * 实现PublisherPresenter的单元测试
 */
//参考Mockito入门：https://waylau.com/mockito-quick-start/
class PublisherPresenterTest {
    private lateinit var PUBLISHERS: List<Publisher>
    private lateinit var mPublisher: Publisher
    @Mock
    private lateinit var mPublisherRepository: PublisherRepository
    @Mock
    private lateinit var mPublisherView: PublisherContract.View

    private lateinit var mPublisherPresenter: PublisherPresenter

    @Before
    fun setupPublisherPresenter() {
        //异常:org.mockito.exceptions.base.MockitoException:
        //Cannot mock/spy class workshop1024.com.xproject.main.publisher.data.source.PublisherRepository
        //Mockito cannot mock/spy following:
        //  - final classes
        //问题：Kotlin的class默认都是final
        //参考：
        // https://antonioleiva.com/mockito-2-kotlin/
        // Mocking final types, enums and final methods (Since 2.1.0)/Configuration-free inline mock making:https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html
        // Mockito通过使用@mock注解，非常方便的注入mock。为了在测试中注入mock，initMocks方法必须被调用。
        MockitoAnnotations.initMocks(this)

        mPublisherPresenter = PublisherPresenter(mPublisherRepository, mPublisherView)
        mPublisher = Publisher("p401", "t005", "l001", "/imag1", "The Gaming", "970601 subscribers", ObservableBoolean(false))

        PUBLISHERS = listOf<Publisher>(
                Publisher("p001", "t001", "l001", "/imag1", "The Tech-mock", "970601 subscribers", ObservableBoolean(false)),
                Publisher("p101", "t002", "l001", "/imag1", "The News", "970601 subscribers", ObservableBoolean(false)),
                Publisher("p401", "t005", "l001", "/imag1", "The Gaming", "970601 subscribers", ObservableBoolean(false))
        )
    }

    @Test
    fun getPublishersByContentTypeAndRefreshList() {
        mPublisherPresenter.getPublishersByContentType(mPublisher.mType)

        argumentCaptor<PublisherDataSource.LoadPublisherAndPublisherTypeCallback>().apply {
            //异常：java.lang.IllegalStateException: mLoadPublisherAndPublish…eCallbackCaptor.capture() must not be null
            //方案：引用com.nhaarman.mockitokotlin2:mockito-kotlin库，处理了不兼容问题
            //参考：https://stackoverflow.com/questions/34773958/kotlin-and-argumentcaptor-illegalstateexception
            //FIXME 但是为什么会不兼容，可以后续多研究
            //异常：org.mockito.exceptions.misusing.InvalidUseOfMatchersException:
            //Invalid use of argument matchers!
            //2 matchers expected, 1 recorded:
            //-> at com.nhaarman.mockitokotlin2.KArgumentCaptor.capture(ArgumentCaptor.kt:198)
            //方案：参数不是传递contentType，而是传递anyString()
            verify(mPublisherRepository).getPublishersByContentType(ArgumentMatchers.anyString(), capture())
            firstValue.onRemotePublishersLoaded(PUBLISHERS)
        }

        val inOrder = inOrder(mPublisherView)
        inOrder.verify(mPublisherView).setLoadingIndicator(true)
        inOrder.verify(mPublisherView).setPublisherIdlingResouce(false)
        inOrder.verify(mPublisherView).refreshPublisherList(PUBLISHERS)
        inOrder.verify(mPublisherView).showSnackBar("Fetch remote " + PUBLISHERS.size + " publishers ...")
        inOrder.verify(mPublisherView).setLoadingIndicator(false)
        inOrder.verify(mPublisherView).setPublisherIdlingResouce(true)
    }

    @Test
    fun getPublishersByLanguageTypeRefreshList() {
        `when`(mPublisherRepository.getIsRequestRemote()).thenReturn(false)

        mPublisherPresenter.getPublishersByLanguageType(mPublisher.mLanguage)
        argumentCaptor<PublisherDataSource.LoadPublisherAndPublisherTypeCallback>().apply {
            verify(mPublisherRepository).getPublishersByLanguageType(ArgumentMatchers.anyString(), capture())
            firstValue.onCacheOrLocalPublishersLoaded(PUBLISHERS)
        }

        val inOrder = inOrder(mPublisherView)
        inOrder.verify(mPublisherView).setLoadingIndicator(true)
        inOrder.verify(mPublisherView).setPublisherIdlingResouce(false)
        inOrder.verify(mPublisherView).refreshPublisherList(PUBLISHERS)
        inOrder.verify(mPublisherView).showSnackBar("Fetch cacheorlocal " + PUBLISHERS.size + " publishers ...")
        inOrder.verify(mPublisherView).setLoadingIndicator(false)
        inOrder.verify(mPublisherView).setPublisherIdlingResouce(true)
    }

    @Test
    fun subscribePublisherShowSnackBar() {
        mPublisherPresenter.subscribePublisher(mPublisher)

        verify(mPublisherRepository).subscribePublisherById(mPublisher.mPublisherId)
        verify(mPublisherView).showSnackBar(mPublisher.mName + " selected")
    }

    @Test
    fun unSubscribePublisherShowSackBar() {
        mPublisherPresenter.unSubscribePublisher(mPublisher)
        verify(mPublisherRepository).unSubscribePublisherById(mPublisher.mPublisherId)
        verify(mPublisherView).showSnackBar(mPublisher.mName + " unselected")
    }
}