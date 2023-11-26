package net.nebula.api.Runnable;

import net.nebula.client.logger.LoggerManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

/**
 * 线程池
 */
public class CoreThreadExcutor {

    private final ExecutorService executorService;
    //线程池名称
    private final String excutorName;

    /**
     * 初始化线程池
     * @param queueCapacity 设置等待队列可以容纳的任务数量上限
     * @param corePoolSize 核心线程池大小，表示线程池中能同时运行的线程数量
     * @param maximumPoolSize 线程池的最大线程数量，表示线程池中允许创建的最大线程数。
     * @param keepAliveTime 当线程池中的线程数大于核心线程数时，空闲线程的存活时间
     * @param factory 自定义的线程工厂
     */
    public CoreThreadExcutor(String excutorName, int queueCapacity, int corePoolSize, int maximumPoolSize, int keepAliveTime, CoreThreadFactory factory) {
        //参数检查
        if (corePoolSize <= 0 || maximumPoolSize < corePoolSize || keepAliveTime <= 0 || excutorName == null) {
            throw new IllegalArgumentException();
        }

        this.excutorName = excutorName;

        executorService = new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS, //以秒为单位
                new ArrayBlockingQueue<>(queueCapacity),
                factory, //线程工厂，用于创建新线程
                new ThreadPoolExecutor.AbortPolicy() //当队列和线程池都满了，无法接受新任务时，抛出RejectedExecutionException异常
        );

    }

    /**
     * 返回一个默认的线程工厂
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull CoreThreadFactory defaultThreadFactory(){
        return new CoreThreadFactory("Core");
    }

    /**
     * 提交一个任务
     * @param r 任务
     */
    final public void execute(Runnable r) {
        executorService.execute(r);
    }

    /**
     * 取消一个任务
     * @param r 任务
     * @return 为false代表取消失败(通常代表任务不存在)
     */
    final public boolean cancel(Runnable r){
        Future<?> feature = executorService.submit(r);
        return feature.cancel(true);
    }

    /**
     * 关闭线程池
     */
    final public void destroy() {
        executorService.shutdown();
        LoggerManager.getLogger().info("Close thread pool -> "+excutorName);
    }
}