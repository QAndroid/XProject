package workshop1024.com.xproject.login.controller.native

import org.junit.Assert.assertEquals
import org.junit.Test
import workshop1024.com.xproject.native.NativeLib
import java.io.File

class NativeLibTest {
    @Test
    fun test_stringFromJNI() {
        //FIXME 在自动化测试的时候自动打包dylib包，目前还只能提前手工打包
        //参考：https://www.jianshu.com/p/e5270ba1c11d

        //java.lang.UnsatisfiedLinkError: Directory separator should not appear in library name
        //no suitable image found.  Did find: libnative-lib.dylib: file too short
        //System.load，参数必须是绝对路劲
        //参考：System.load 和 System.loadLibrary区别，https://blog.csdn.net/wwlwwy89/article/details/41147413
        val nativeFile = File("build/dylibs/libnative-lib.dylib")
        System.load(nativeFile.absolutePath)
        val nativeLib = NativeLib()
        assertEquals("Hello from C++", nativeLib.stringFromJNI())
    }
}