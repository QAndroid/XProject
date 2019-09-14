package workshop1024.com.xproject.home.model

import workshop1024.com.xproject.home.model.subinfo.source.SubInfoDataSource
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoMockRepository

object Injection {

    fun provideSubInfoRepository(): SubInfoDataSource {
        return SubInfoMockRepository.instance
    }
}