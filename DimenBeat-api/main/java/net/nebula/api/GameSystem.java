package net.nebula.api;

import net.nebula.api.Runnable.CoreThreadExcutor;
import net.nebula.api.event.EventExecuter;

public abstract class GameSystem {
    /**
     * 所有事件的 注册/解绑 调度器
     */
    public static volatile EventExecuter eventExecuter = new EventExecuter();
    /**
     * API的线程调度器
     * <p>
     * 允许32个线程在等待队列
     * <p>
     * 最多能有四个线程在运行，线程池总数量为16个线程
     * <p>
     * 空闲线程存活时间为60秒
     */
    public static volatile CoreThreadExcutor EventThreadExcutor = new CoreThreadExcutor("Event",32,8,16,60,CoreThreadExcutor.defaultThreadFactory());


}
