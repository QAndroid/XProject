package workshop1024.com.xproject.base.room.converts

import androidx.databinding.ObservableBoolean
import com.google.gson.Gson
import org.junit.Assert.assertEquals
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
        //FIXME ObservableBoolean没有实现equals方法，如何Assert？
        assertEquals(mFalseObservable, XConverters().dateToObservableBoolean(false))
        assertEquals(mTrueObservable, XConverters().dateToObservableBoolean(true))
    }

    @Test
    fun fromIsObservableBoolean() {

    }
}