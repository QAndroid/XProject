package workshop1024.com.xproject.login.controller.native

class NativeLib {
    //一个由'native-lib'Native库实现的Native方法，它和这个应用一起打包
    external fun stringFromJNI(): String

    //伴生对象类的唯一对象，声明的方法和成员都是类的唯一值
    companion object {
        //使用在应用启动时加载的'native-lib'库
        init {
            System.loadLibrary("native-lib")
        }
    }
}