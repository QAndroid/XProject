package workshop1024.com.xproject.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 线程执行器工具类，包含了磁盘IO线程执行器，主线程执行器等，用于根据不同的业务场景，选择适当的执行器执行线程异步业务任务
 */
public class ExecutorUtils {
    //磁盘IO线程执行器，用于执行本地数据库缓冲异步任务
    private final Executor mDiskIOExecutor;
    //主线程执行器，用于执行主线程IO异步任务
    private final Executor mMainThreadExecutor;

    ExecutorUtils(Executor diskIOExecutor, Executor mainThreadExecutor) {
        mDiskIOExecutor = diskIOExecutor;
        mMainThreadExecutor = mainThreadExecutor;
    }

    public ExecutorUtils() {
        this(new DiskIOThreadExecutor(), new MainThreadExecutor());
    }

    /**
     * 获取磁盘IO线程执行器
     *
     * @return 磁盘IO线程执行器
     */
    public Executor getDiskIOExecutor() {
        return mDiskIOExecutor;
    }

    /**
     * 获取主线程执行器
     *
     * @return 主线程执行器
     */
    public Executor getMainThreadExecutor() {
        return mMainThreadExecutor;
    }

    /**
     * 磁盘IO线程执行器
     */
    private static class DiskIOThreadExecutor implements Executor {
        private Executor mDiskIOThreadExecutor = Executors.newSingleThreadExecutor();

        @Override
        public void execute(@NonNull Runnable runnable) {
            mDiskIOThreadExecutor.execute(runnable);
        }
    }

    /**
     * 主线程执行器
     */
    private static class MainThreadExecutor implements Executor {
        private Handler mMainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mMainThreadHandler.post(runnable);
        }
    }
}
