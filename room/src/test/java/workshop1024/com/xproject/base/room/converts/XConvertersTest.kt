package workshop1024.com.xproject.base.room.converts

import androidx.databinding.ObservableBoolean
import com.google.gson.Gson
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

class XConvertersTest {
    //FIXME JUNIT边界测试是如何思考和定义的
    //空List
    private val mEmptyList: List<String> = emptyList()
    //2个元素List
    private val mTwoList: List<String> = listOf("f001", "f002")

    private val mFalseObservable: ObservableBoolean = ObservableBoolean(false)
    private val mTrueObservable: ObservableBoolean = ObservableBoolean(true)

    @Test
    fun dateToList() {
        //测试空List转换
        //assertEquals：查看两个对象是否相等，类似于字符串比较使用的equals()方法
        //assertSame：查看两个对象的引用是否相等
        //参考：https://www.cnblogs.com/lyy-2016/p/6116814.html
        assertEquals(mEmptyList, XConverters().dateToList(Gson().toJson(mEmptyList)))
        //测试2个元素List转换
        assertEquals(mTwoList, XConverters().dateToList(Gson().toJson(mTwoList)))
    }

    @Test
    fun fromIsList() {
        //测试空List转换
        assertEquals(Gson().toJson(mEmptyList), XConverters().fromIsList(mEmptyList))
        //测试2个元素List转换
        assertEquals(Gson().toJson(mTwoList), XConverters().fromIsList(mTwoList))
    }

    @Test
    fun dateToObservableBoolean() {
        assertObservableBoolean(false, XConverters().dateToObservableBoolean(false))
        assertObservableBoolean(true, XConverters().dateToObservableBoolean(true))
    }

    @Test
    fun fromIsObservableBoolean() {
        assertEquals(true, XConverters().fromIsObservableBoolean(mTrueObservable))
        assertEquals(false, XConverters().fromIsObservableBoolean(mFalseObservable))
    }

    //比较ObservableBoolean只需要比较保存的Boolean值相等
    private fun assertObservableBoolean(expectedBoolean: Boolean, actualObser: ObservableBoolean?) {
        //assertThat：查看实际值是否满足指定的条件
        //参考：https://www.cnblogs.com/lyy-2016/p/6116814.html
        assertThat(actualObser?.get(), equalTo(expectedBoolean))
    }
}