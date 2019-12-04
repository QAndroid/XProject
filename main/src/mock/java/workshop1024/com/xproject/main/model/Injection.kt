package workshop1024.com.xproject.main.model

import android.content.Context
import workshop1024.com.xproject.base.utils.ExecutorUtils
import workshop1024.com.xproject.main.filter.data.source.FilterDataSource
import workshop1024.com.xproject.main.filter.data.source.FilterRepository
import workshop1024.com.xproject.main.filter.data.source.local.FilterDatabase
import workshop1024.com.xproject.main.filter.data.source.local.FilterLocalDataSource
import workshop1024.com.xproject.main.filter.data.source.remote.FilterRemoteDataSource
import workshop1024.com.xproject.main.publisher.data.source.local.PublisherDatabase
import workshop1024.com.xproject.main.publisher.data.source.local.PublisherLocalDataSource
import workshop1024.com.xproject.main.publisher.data.source.remote.PublisherRemoteDataSource
import workshop1024.com.xproject.main.publisher.data.source.PublisherDataSource
import workshop1024.com.xproject.main.publisher.data.source.PublisherRepository

object Injection {

    fun providePublisherRepository(context: Context): PublisherDataSource {
        return PublisherRepository.getInstance(PublisherRemoteDataSource.instance,
                PublisherLocalDataSource.getInstance(PublisherDatabase.getInstance(context).publisherDao(), ExecutorUtils()))
    }

    fun provideFilterRepository(context: Context): FilterDataSource {
        return FilterRepository.getInstance(FilterRemoteDataSource.instance,
                FilterLocalDataSource.getInstance(FilterDatabase.getInstance(context).filterDao(), ExecutorUtils()))
    }
}