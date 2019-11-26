package workshop1024.com.xproject.base.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UnitUtilsTest {
    @Test
    fun test_dpToPx() {
        //使用mockito来Mock Andorid系统APIcontext.resources.displayMetrics.scaledDensity
        val mockContext = mock(Context::class.java)
        val mockResources = mock(Resources::class.java)
        val mockDisplayMetrics = mock(DisplayMetrics::class.java)
        `when`(mockContext.getResources()).thenReturn(mockResources)
        `when`(mockResources.getDisplayMetrics()).thenReturn(mockDisplayMetrics)
        mockDisplayMetrics.scaledDensity = 1.5f

        //调用dpToPx进行单位转换，并断言结果
        val px = UnitUtils.dpToPx(mockContext, 1f);
        assertEquals(2, px);
    }
}