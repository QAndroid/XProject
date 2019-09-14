package workshop1024.com.xproject.home.model

import workshop1024.com.xproject.home.model.subinfo.source.SubInfoDataSource
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoRepository

object Injection {

    fun provideSubInfoRepository(): SubInfoDataSource {
        return SubInfoRepository.instance
    }
}
