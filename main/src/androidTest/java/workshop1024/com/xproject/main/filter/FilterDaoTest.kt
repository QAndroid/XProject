package workshop1024.com.xproject.main.filter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import workshop1024.com.xproject.main.model.filter.Filter
import workshop1024.com.xproject.main.model.filter.source.local.FilterDao
import workshop1024.com.xproject.main.model.filter.source.local.FilterDatabase

class FilterDaoTest {
    private lateinit var filterDatabase: FilterDatabase
    private lateinit var filterDao: FilterDao

    //InstantTaskExecutorRule：用于Architecture Components的测试，可以将默认使用的后台executor转为同步执行，让测试可以马上获得结果
    //Rule规则，参考：https://www.jianshu.com/p/e7147839c452
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    //显式使用 runBlocking 创建一个新的协程同时阻塞当前线程，直到协程结束
    //参考runBlocking，https://johnnyshieh.me/posts/kotlin-coroutine-introduction/
    //FIXME 既然都阻塞了，为什么不直接写
    @Before
    fun createDb() = runBlocking {
        //targetContext，返回目标应用的应用上下文
        //参考：Tips关键，https://www.jianshu.com/p/053576f10d7d
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        //将数据库建在内存中，可以让你的测试整体更加一体化，更密闭。
        //参考：测试数据库，https://www.jianshu.com/p/6cb36419e62c
        filterDatabase = Room.inMemoryDatabaseBuilder(context, FilterDatabase::class.java).build()

        //插入测试Filter数据
        filterDao = filterDatabase.filterDao()
        filterDao.addFilter(testFilter1)
        filterDao.addFilter(testFilter2)
    }

    @After
    fun closeDb() = runBlocking {
        filterDatabase.close()
    }

    @Test
    fun testAddFilter() {
        filterDao.addFilter(Filter("f003", "usa"))
        assertThat(filterDao.getFilters().size, CoreMatchers.equalTo(3))
    }
}