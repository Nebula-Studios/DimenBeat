package net.nebula.api.Runnable;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

public class CoreThreadFactory implements ThreadFactory {

    private final String threadName;
    //线程计数器
    private volatile int counter;
    //控制是否覆盖线程名
    private final boolean override;

    /**
     * 初始化线程工厂
     * @param threadName 线程名字(统一的)
     */
    public CoreThreadFactory(String threadName){
        this.threadName = threadName;
        this.override = false;
    }

    /**
     * 初始化线程工厂
     * @param threadName 线程名字
     * @param override 是否取消覆盖，为true则不覆盖名字
     */
    public CoreThreadFactory(String threadName,boolean override){
        this.threadName = threadName;
        this.override = override;
    }


    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread thread = new Thread(r);
        //使用同步锁，防止线程计数器出现问题
        if (!override){
            synchronized (this){
                thread.setName("Thread-"+threadName+"-"+counter);
                counter++;
            }
        }
        return thread;
    }
}
