package workshop1024.com.xproject.main.controller.activity

import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import workshop1024.com.xproject.main.R
import workshop1024.com.xproject.main.controller.action.RecyclerViewItemActions.Companion.actionByChildId
import workshop1024.com.xproject.main.controller.action.RecyclerViewItemActions.Companion.childViewByPositionWithMatcher
import workshop1024.com.xproject.main.controller.adapter.PublisherListAdapter.PublisherViewHolder
import workshop1024.com.xproject.main.model.Injection
import workshop1024.com.xproject.main.model.publisher.Publisher
import workshop1024.com.xproject.main.model.publisher.PublisherType
import workshop1024.com.xproject.main.model.publisher.source.PublisherDataSource

//Espresso RecyclerView操作操作相关API，参考：https://developer.android.com/training/testing/espresso/lists
//Espresso Iding Resourdde，用于异步测试，参考：https://developer.android.com/training/testing/espresso/idling-resource
@RunWith(AndroidJUnit4::class)
class PublisherActivityTest {
    private lateinit var mPublisherRepository: PublisherDataSource

    private var mPublisherIdlingResource: IdlingResource? = null

    @Before
    fun initRepository() {
        mPublisherRepository = Injection.providePublisherRepository(getApplicationContext())
    }

    @Test
    fun mockOnePublisher_CheckItemShow() {
        //测试前构造数据：删除所有Item数据，Mock一个Item数据
        mPublisherRepository.deleteAllPublishers();
        mPublisherRepository.savePublisher(Publisher("p001", "t001", "l001", "/imag1", "The Tech-mock", "970601 subscribers", ObservableBoolean(true)))

        //启动PublisherList页面
        launcherActivity();

        //只有一个Item，直接使用Text和Id等匹配
        onView(withText("The Tech-mock")).check(matches(isDisplayed()))
        onView(withText("970601 subscribers")).check(matches(isDisplayed()))
        onView(withId(R.id.publisherlist_checkbox_selected)).check(matches(isChecked()))
    }

    @Test
    fun mockMutilPublisher_scrollPosition_CheckItemShow() {
        //测试前构造数据：删除所有Item数据，Mock多个Item数据
        mPublisherRepository.deleteAllPublishers();
        mockMutilPublisher()

        //启动PublisherList页面
        launcherActivity();
        //首先滑动到列表指定的位置，使用scrollToPosition
        onView(withId(R.id.publisher_recyclerview_list)).perform(scrollToPosition<ViewHolder>(7))

        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_textview_name, 7, isDisplayed())))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_textview_name, 7, withText("36氪"))))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_textview_subscribenum, 7, isDisplayed())))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_textview_subscribenum, 7, withText("1253456 subscribers"))))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_checkbox_selected, 7, isDisplayed())))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_checkbox_selected, 7, isNotChecked())))
    }

    @Test
    fun mockMutilPublisher_scrollToMiddle_CheckMiddleItem() {
        //测试前构造数据：删除所有Item数据，Mock多个Item数据
        mPublisherRepository.deleteAllPublishers();
        mockMutilPublisher()

        //启动PublisherList页面
        launcherActivity();

        //滑动到指定Holder，列表中间，使用scrollToHolder
        onView(withId(R.id.publisher_recyclerview_list)).perform(scrollToHolder(isInTheMiddle()))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_textview_name, 11, isDisplayed())))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_textview_name, 11, withText("Lifehacker-mock"))))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_textview_subscribenum, 11, isDisplayed())))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_textview_subscribenum, 11, withText("934274 subscribers"))))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_checkbox_selected, 11, isDisplayed())))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_checkbox_selected, 11, isNotChecked())))
    }

    @Test
    fun mockMutilPublisher_ScrollToMiddle_Click_CheckItem() {
        //测试前构造数据：删除所有Item数据，Mock多个Item数据
        mPublisherRepository.deleteAllPublishers();
        mockMutilPublisher()

        //启动PublisherList页面
        launcherActivity();

        //滑动到指定位置2，并且指定id，进行点击
        onView(withId(R.id.publisher_recyclerview_list)).perform(actionOnItemAtPosition<ViewHolder>(2,
                actionByChildId(click(), R.id.publisherlist_checkbox_selected)))
        onView(withId(R.id.publisher_recyclerview_list)).check(matches(childViewByPositionWithMatcher(R.id.publisherlist_checkbox_selected, 2, isChecked())))
    }

    @Test
    fun mockMutilPublisher_ClickPublisherType_CheckDialog() {
        //测试前构造数据：删除所有Item数据，Mock多个Item数据
        mPublisherRepository.deleteAllPublishers();
        mPublisherRepository.deleteAllPublisherTypes();
        mockMutilPublisher()
        mockMutilPublisherType()

        //启动PublisherList页面
        launcherActivity();

        //点击发布类型选择按钮
        onView(withId(R.id.publisher_menu_filter)).perform(click())
        //检查发布类型对话框展示
        onView(withText("Select Publisher")).check(matches(isDisplayed()))
        onView(withText("Tech")).check(matches(isDisplayed()))
        onView(withText("News")).check(matches(isDisplayed()))
        onView(withText("Business")).check(matches(isDisplayed()))
    }

    @Test
    fun mockPublisherAndType_SelectPublisherType_CheckItems() {
        //测试前构造数据：删除所有Item数据，Mock多个Item数据
        mPublisherRepository.deleteAllPublishers();
        mPublisherRepository.deleteAllPublisherTypes();
        mockMutilPublisher()
        mockMutilPublisherType()

        //启动PublisherList页面
        launcherActivity();
        //点击发布类型选择按钮，选中Tech类型
        onView(withId(R.id.publisher_menu_filter)).perform(click())
        onView(withText("Tech")).perform(click())

        //检查列表的展示
        onView(withText("The Tech-mock")).check(matches(isDisplayed()))
        onView(withText("Engadget")).check(matches(isDisplayed()))
        onView(withText("Lifehacker")).check(matches(isDisplayed()))
        onView(withText("ReadWrite-mock")).check(matches(isDisplayed()))
    }

    //TODO 语言对话框的测试

    private fun mockMutilPublisherType() {
        mPublisherRepository.savePublisherType(PublisherType("t001", "content", "Tech"))
        mPublisherRepository.savePublisherType(PublisherType("t002", "content", "News"))
        mPublisherRepository.savePublisherType(PublisherType("t003", "content", "Business"))
    }

    private fun mockMutilPublisher() {
        mPublisherRepository.savePublisher(Publisher("p001", "t001", "l001", "/imag1", "The Tech-mock", "970601 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p002", "t001", "l001", "/imag1", "Engadget", "1348433 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p003", "t001", "l001", "/imag1", "Lifehacker", "934275 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p004", "t001", "l001", "/imag1", "ReadWrite-mock", "254332 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p005", "t001", "l001", "/imag1", "Digital Trends", "145694 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p006", "t001", "l001", "/imag1", "Business Insider", "331892 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p007", "t001", "l003", "/imag1", "月光博客", "254321 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p008", "t001", "l003", "/imag1", "36氪", "1253456 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p009", "t001", "l001", "/imag1", "TechCrunch-mock", "994287 subscribers", ObservableBoolean(false)))

        mPublisherRepository.savePublisher(Publisher("p101", "t002", "l001", "/imag1", "The News", "970601 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p102", "t002", "l001", "/imag1", "Engadget", "1348433 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p103", "t002", "l001", "/imag1", "Lifehacker-mock", "934274 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p104", "t002", "l001", "/imag1", "ReadWrite", "254332 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p105", "t002", "l001", "/imag1", "Digital Trends", "145694 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p106", "t002", "l001", "/imag1", "Business Insider", "331892 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p107", "t002", "l003", "/imag1", "今日头条", "254321 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p108", "t002", "l003", "/imag1", "腾讯新闻", "125345 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p109", "t002", "l001", "/imag1", "TechCrunch", "994287 subscribers", ObservableBoolean(false)))

        mPublisherRepository.savePublisher(Publisher("p201", "t003", "l001", "/imag1", "The Business", "970601 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p202", "t003", "l001", "/imag1", "The New York Times", "1348433 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p203", "t003", "l001", "/imag1", "OZY-mock", "934273 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p204", "t003", "l001", "/imag1", "ABC News", "254332 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p205", "t003", "l001", "/imag1", "FOX News", "145694 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p206", "t003", "l001", "/imag1", "NRP News", "331892 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p207", "t003", "l003", "/imag1", "财经周刊", "254321 subscribers", ObservableBoolean(true)))
        mPublisherRepository.savePublisher(Publisher("p208", "t003", "l003", "/imag1", "交易时刻", "125345 subscribers", ObservableBoolean(false)))
        mPublisherRepository.savePublisher(Publisher("p209", "t003", "l001", "/imag1", "BBC", "994287 subscribers", ObservableBoolean(false)))
    }

    private fun launcherActivity() {
        val scenario = ActivityScenario.launch(PublisherActivity::class.java)
        //在PublisherActivity的主线程运行指定ActivityAction
        scenario.onActivity(object : ActivityScenario.ActivityAction<PublisherActivity> {
            override fun perform(activity: PublisherActivity?) {
                //开始运行每个case之前，获取并注册PublisherIdlingResouce
                mPublisherIdlingResource = activity?.mPublisherIdlingResouce
                //为了证明测试失败，省略这个调用
                IdlingRegistry.getInstance().register(mPublisherIdlingResource)
            }
        })
    }

    @After
    fun unregisterIdlingResource() {
        //运行完每个Case之后，注销PublisherIdlingResource
        if (mPublisherIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mPublisherIdlingResource)
        }
    }

    companion object {
        /**
         * 定义isInTheMiddle，判断列表是否滑动到中间
         */
        private fun isInTheMiddle(): Matcher<PublisherViewHolder> {
            return object : TypeSafeMatcher<PublisherViewHolder>() {
                override fun matchesSafely(item: PublisherViewHolder): Boolean {
                    return item.mIsInTheMiddle;
                }

                override fun describeTo(description: Description?) {
                    description!!.appendText("item in the end")
                }
            };
        }
    }
}