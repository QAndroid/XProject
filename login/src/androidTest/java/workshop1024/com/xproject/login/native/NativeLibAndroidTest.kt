package workshop1024.com.xproject.login.native

import org.junit.Test
import org.junit.Assert.assertEquals

//NDK单元测试，自由在Android环境，Sytem.loadLibrary()加载so库才可以
//在JVM环境，要根据运行本机平台，选择合适的加载方式。如mac使用dylib
//参考：https://www.jianshu.com/p/e5270ba1c11d
class NativeLibAndroidTest {
    @Test
    fun test_stringFromJNI(){
        //System.loadLibrary，参数直接是库名字，不加前缀和后缀
        //参考：System.load 和 System.loadLibrary区别，https://blog.csdn.net/wwlwwy89/article/details/41147413
        System.loadLibrary("native-lib")
        val nativeLib = NativeLib()
        assertEquals("Hello from C++",nativeLib.stringFromJNI())
    }
}