package workshop1024.com.xproject.main.publisher.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.ObservableBoolean
import com.nhaarman.mockitokotlin2.argumentCaptor
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import workshop1024.com.xproject.main.LiveDataTestUtil
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.source.PublisherDataSource
import workshop1024.com.xproject.main.publisher.data.source.PublisherRepository

class PublisherViewModelTest {
    private lateinit var PUBLISHERS: List<Publisher>
    private lateinit var mPublisher: Publisher

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mPublisherRepository: PublisherRepository

    private lateinit var mPublisherViewModel: PublisherViewModel

    @Before
    fun setupPublisherViewModel() {
        MockitoAnnotations.initMocks(this)

        mPublisherViewModel = PublisherViewModel(mPublisherRepository)

//        mPublisher = Publisher("p401", "t005", "l001", "/imag1", "The Gaming", "970601 subscribers", ObservableBoolean(false))
//        PUBLISHERS = listOf<Publisher>(
//                Publisher("p001", "t001", "l001", "/imag1", "The Tech-mock", "970601 subscribers", ObservableBoolean(false)),
//                Publisher("p101", "t002", "l001", "/imag1", "The News", "970601 subscribers", ObservableBoolean(false)),
//                Publisher("p401", "t005", "l001", "/imag1", "The Gaming", "970601 subscribers", ObservableBoolean(false))
//        )
    }

    @Test
    fun getPublishersByContentTypeAndRefreshList() {
//        mPublisherViewModel.getPublishersByContentType(mPublisher.mType)
//        assertTrue(LiveDataTestUtil.getValue(mPublisherViewModel.mIsLoading))

//        argumentCaptor<PublisherDataSource.LoadPublisherAndPublisherTypeCallback>().apply {
//            //异常：java.lang.IllegalStateException: mLoadPublisherAndPublish…eCallbackCaptor.capture() must not be null
//            //方案：引用com.nhaarman.mockitokotlin2:mockito-kotlin库，处理了不兼容问题
//            //参考：https://stackoverflow.com/questions/34773958/kotlin-and-argumentcaptor-illegalstateexception
//            //FIXME 但是为什么会不兼容，可以后续多研究
//            //异常：org.mockito.exceptions.misusing.InvalidUseOfMatchersException:
//            //Invalid use of argument matchers!
//            //2 matchers expected, 1 recorded:
//            //-> at com.nhaarman.mockitokotlin2.KArgumentCaptor.capture(ArgumentCaptor.kt:198)
//            //方案：参数不是传递contentType，而是传递anyString()
//            verify(mPublisherRepository).getPublishersByContentType(ArgumentMatchers.anyString(), capture())
//            firstValue.onRemotePublishersLoaded(PUBLISHERS)
//        }
//
//        assertFalse(LiveDataTestUtil.getValue(mPublisherViewModel.mPublisherList).isEmpty())
//        assertTrue(LiveDataTestUtil.getValue(mPublisherViewModel.mPublisherList).size == 3)
    }
}