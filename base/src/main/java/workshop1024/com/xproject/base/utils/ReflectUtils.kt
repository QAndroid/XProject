package workshop1024.com.xproject.base.utils

import android.util.Log
import workshop1024.com.xproject.base.exception.ReflectClassNotFoundException
import kotlin.jvm.internal.Reflection
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.declaredFunctions

/**
 * 反射相关工具类
 */
object ReflectUtils {

    /**
     * 调用指定类中Companion对象中的方法
     * @param classPath 调用类的路径
     * @param methodName 调用companion对象中方法名称
     * @param params 调用方法中的参数
     * 参考：https://blog.csdn.net/qq_37299249/article/details/84304001
     */
    //Kotlin没有受检查异常，Kotlin函数不会声明抛出异常，Kotlin使用@Throws注解
    //参考：受检异常-https://www.kotlincn.net/docs/reference/java-to-kotlin-interop.html#checked-exceptions
    @Throws(ReflectClassNotFoundException::class)
    fun invokeCompanionMethod(classPath: String, methodName: String, vararg params: Any): Any {
        try {
            val clazz = Class.forName(classPath)
            val kotlinClazz = Reflection.createKotlinClass(clazz)
            val kotlinFunction = kotlinClazz.companionObject?.declaredFunctions?.find { it.name == methodName }
            //*params使用伸展操作符，在数组面前加*
            //参考：可变数量的参数（Varargs），https://www.kotlincn.net/docs/reference/functions.html#%E5%8F%AF%E5%8F%98%E6%95%B0%E9%87%8F%E7%9A%84%E5%8F%82%E6%95%B0varargs
            return kotlinFunction?.call(kotlinClazz.companionObjectInstance, *params)!!
        } catch (exception: ClassNotFoundException) {
            //参考：提倡异常封装——《改善Java程序的151个建议》，分类处理异常如下：
            //1.方便开发和维护人员，打印异常堆栈
            exception.printStackTrace()
            //2.提高日后维护，细明确输出错误日志
            Log.e("XProject", "你调用的方法:${methodName}，所在的类:${classPath}不存在!")
            //3.抛出业务异常，使用者了解具体原因
            throw ReflectClassNotFoundException("你调用的方法:${methodName}，所在的类:${classPath}不存在!", exception)
        }
    }
}