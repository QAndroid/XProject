package workshop1024.com.xproject.home.model

object Injection {

    fun provideSubInfoRepository(context: Context): SubInfoDataSource {
        return SubInfoRepository.(SubInfoRemoteDataSource.instance,
                SubInfoLocalDataSource.getInstance(SubInfoDatabase.getInstance(context).subInfoDao(), ExecutorUtils()))
    }
}
