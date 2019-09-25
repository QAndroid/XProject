package workshop1024.com.xproject.base.utils

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * 线程执行器工具类，包含了磁盘IO线程执行器，主线程执行器等，用于根据不同的业务场景，选择适当的执行器执行线程异步业务任务
 */
class ExecutorUtils internal constructor(//磁盘IO线程执行器，用于执行本地数据库缓冲异步任务
        val mDiskIOExecutor: Executor, //主线程执行器，用于执行主线程IO异步任务
        val mMainThreadExecutor: Executor) {

    constructor() : this(DiskIOThreadExecutor(), MainThreadExecutor())

    /**
     * 磁盘IO线程执行器
     */
    private class DiskIOThreadExecutor : Executor {
        private val mDiskIOThreadExecutor = Executors.newSingleThreadExecutor()

        override fun execute(runnable: Runnable) {
            mDiskIOThreadExecutor.execute(runnable)
        }
    }

    /**
     * 主线程执行器
     */
    private class MainThreadExecutor : Executor {
        private val mMainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(runnable: Runnable) {
            mMainThreadHandler.post(runnable)
        }
    }
}
