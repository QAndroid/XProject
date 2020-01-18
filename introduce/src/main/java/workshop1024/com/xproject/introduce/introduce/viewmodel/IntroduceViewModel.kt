package workshop1024.com.xproject.introduce.introduce.viewmodel

import android.util.MutableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import workshop1024.com.xproject.introduce.R

class IntroduceViewModel : ViewModel() {
    //当前页面展示的页数
    private val _pagePostion = MutableLiveData<Int>().apply { value = 0 }
    val mpagePostion: LiveData<Int>
        get() = _pagePostion

    //介绍布局id
    val mLayoutIdList = listOf(R.layout.introduce1_fragment, R.layout.introduce2_fragment, R.layout.introduce3_fragment)

    fun onPageSelected(position: Int) {
        _pagePostion.value = position
    }
}