package workshop1024.com.xproject.main.model

import android.content.Context
import workshop1024.com.xproject.base.utils.ExecutorUtils
import workshop1024.com.xproject.main.model.publisher.local.PublisherDatabase
import workshop1024.com.xproject.main.model.publisher.local.PublisherLocalDataSource
import workshop1024.com.xproject.main.model.publisher.remote.PublisherRemoteDataSource
import workshop1024.com.xproject.main.model.publisher.source.PublisherDataSource
import workshop1024.com.xproject.main.model.publisher.source.PublisherRepository
import workshop1024.com.xproject.model.filter.source.FilterMockRepository
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeMockRepository

object Injection {
    fun providePublisherTypeRepository(): workshop1024.com.xproject.main.model.publishertype.source.PublisherTypeDataSource {
        return PublisherTypeMockRepository.instance
    }

    fun providePublisherRepository(context: Context): PublisherDataSource {
        return PublisherRepository.getInstance(PublisherRemoteDataSource.instance,
                PublisherLocalDataSource.getInstance(PublisherDatabase.getInstance(context).publisherDao(), ExecutorUtils()))
    }

    fun provideFilterRepository(): workshop1024.com.xproject.main.model.filter.source.FilterDataSource {
        return FilterMockRepository.instance
    }
}