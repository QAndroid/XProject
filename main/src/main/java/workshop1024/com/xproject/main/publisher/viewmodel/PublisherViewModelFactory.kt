package workshop1024.com.xproject.main.publisher.viewmodel

import android.app.Application
import android.widget.ViewSwitcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import workshop1024.com.xproject.main.model.Injection
import workshop1024.com.xproject.main.publisher.data.source.PublisherDataSource
import workshop1024.com.xproject.main.publisher.data.source.PublisherRepository

class PublisherViewModelFactory private constructor(private val mPublisherDataSource: PublisherDataSource)
    : ViewModelProvider.NewInstanceFactory() {

    //泛型函数：函数也可以有类型参数，类型参数放在函数名称之前
    //参考：https://www.kotlincn.net/docs/reference/generics.html

    //单表达式函数：可以省略花括号并且在=符号之后指定代码体就可以
    //参考：https://www.kotlincn.net/docs/reference/functions.html
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(PublisherViewModel::class.java) ->
                        PublisherViewModel(mPublisherDataSource)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {
        private var INSTANCE: PublisherViewModelFactory? = null

        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(PublisherViewModelFactory::class.java) {
                    INSTANCE ?: PublisherViewModelFactory(Injection.providePublisherRepository(application.applicationContext)).also {
                        INSTANCE = it
                    }
                }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}