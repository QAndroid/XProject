package workshop1024.com.xproject.home.model

import android.content.Context
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoDataSource
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoRepository
import workshop1024.com.xproject.home.model.subinfo.source.local.SubInfoDatabase
import workshop1024.com.xproject.home.model.subinfo.source.local.SubInfoLocalDataSource
import workshop1024.com.xproject.home.model.subinfo.source.remote.SubInfoRemoteDataSource
import workshop1024.com.xproject.base.utils.ExecutorUtils

object Injection {
    fun provideSubInfoRepository(context: Context): SubInfoDataSource {
        return SubInfoRepository.getInstance(SubInfoRemoteDataSource.instance,
                SubInfoLocalDataSource.getInstance(SubInfoDatabase.getInstance(context).subInfoDao(), ExecutorUtils()))
    }
}
