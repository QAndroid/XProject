package workshop1024.com.xproject.home.model

import workshop1024.com.xproject.home.model.subinfo.source.*
import workshop1024.com.xproject.home.model.subinfo.source.local.FilterLocalDataSource
import workshop1024.com.xproject.home.model.subinfo.source.local.SubscribeLocalDataSource
import workshop1024.com.xproject.home.model.subinfo.source.local.TagLocalDataSource
import workshop1024.com.xproject.home.model.subinfo.source.remote.FilterRemoteDataSource
import workshop1024.com.xproject.home.model.subinfo.source.remote.SubscribeRemoteDataSource
import workshop1024.com.xproject.home.model.subinfo.source.remote.TagRemoteDataSource

object Injection {
    fun provideSubscribeRepository(): SubInfoDataSource {
        return SubscribeRepository.getInstance(SubscribeRemoteDataSource.instance, SubscribeLocalDataSource.instance)
    }

    fun provideTagRepository(): SubInfoDataSource {
        return TagRepository.getInstance(TagRemoteDataSource.instance, TagLocalDataSource.instance)
    }

    fun provideFilterRepository(): SubInfoDataSource {
        return FilterRepository.getInstance(FilterRemoteDataSource.instance, FilterLocalDataSource.instance)
    }
}
