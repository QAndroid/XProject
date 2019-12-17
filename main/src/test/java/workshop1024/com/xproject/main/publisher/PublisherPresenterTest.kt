package workshop1024.com.xproject.main.publisher

import androidx.databinding.ObservableBoolean
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.source.PublisherRepository

/**
 * 实现PublisherPresenter的单元测试
 */
//参考Mockito入门：https://waylau.com/mockito-quick-start/
class PublisherPresenterTest {
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
    }

    @Test
    fun subscribePublisherShowSnackBar(){
        val publisher = Publisher("p401", "t005", "l001", "/imag1", "The Gaming", "970601 subscribers", ObservableBoolean(false))
        mPublisherPresenter.subscribePublisher(publisher)

        verify(mPublisherRepository).subscribePublisherById(publisher.mPublisherId)
        verify(mPublisherView).showSnackBar(publisher.mName + " selected")
    }
}