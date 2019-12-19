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

        PUBLISHERS = listOf<Publisher>(
                Publisher("p001", "t001", "l001", "/imag1", "The Tech-mock", "970601 subscribers", ObservableBoolean(false)),
                Publisher("p101", "t002", "l001", "/imag1", "The News", "970601 subscribers", ObservableBoolean(false)),
                Publisher("p401", "t005", "l001", "/imag1", "The Gaming", "970601 subscribers", ObservableBoolean(false))
        )
    }

    @Test
    fun getPublishersByContentTypeAndRefreshList() {
        val contentType = "t001"
        mPublisherPresenter.getPublishersByContentType(contentType)

        argumentCaptor<PublisherDataSource.LoadPublisherAndPublisherTypeCallback>().apply {
            //异常：java.lang.IllegalStateException: mLoadPublisherAndPublish…eCallbackCaptor.capture() must not be null
            //方案：引用com.nhaarman.mockitokotlin2:mockito-kotlin库，处理了不兼容问题
            //参考：https://stackoverflow.com/questions/34773958/kotlin-and-argumentcaptor-illegalstateexception
            verify(mPublisherRepository).getPublishersByContentType(ArgumentMatchers.anyString(), capture())
            PUBLISHERS = listOf<Publisher>(
                    Publisher("p001", "t001", "l001", "/imag1", "The Tech-mock", "970601 subscribers", ObservableBoolean(false))
            )
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
    fun subscribePublisherShowSnackBar() {
        val publisher = Publisher("p401", "t005", "l001", "/imag1", "The Gaming", "970601 subscribers", ObservableBoolean(false))
        mPublisherPresenter.subscribePublisher(publisher)

        verify(mPublisherRepository).subscribePublisherById(publisher.mPublisherId)
        verify(mPublisherView).showSnackBar(publisher.mName + " selected")
    }
}