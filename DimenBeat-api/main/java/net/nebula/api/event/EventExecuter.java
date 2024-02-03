package net.nebula.api.event;

import net.nebula.api.Logger.LoggerManager;
import net.nebula.api.Runnable.ThreadRunnable;
import net.nebula.api.modder.exception.EventException;
import net.nebula.api.Logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件监听器的管理器
 * <p>
 * 提供事件监听器的 注册/解绑/事件传递 功能
 */
public class EventExecuter {
    /**
     * 存储着事件监听器的Map
     * <p>
     * 一个监听器对应一个可以触发的方法(事件传递)
     * <p>
     * 事件系统的速度取决于这里运行的速度
     */
    private final Map<Class<?>, Map<EventListener, Method>> eventListeners;

    private final Logger logger;

    //线程计数器
    private static volatile int counter = 0;

    /**
     * 默认构造方法
     * <p>
     * 用以初始化事件监听器的存储Map
     */
    public EventExecuter() {
        //设置默认大小为256
        eventListeners = new HashMap<>(256);
        logger = new LoggerManager("EventExecuter-"+counter);
        synchronized (this){
            counter++;
        }
    }

    /**
     * 自定义构造方法
     * <p>
     * 用以初始化事件监听器的存储Map
     * @param initialCapacity 设置存储事件监听器的Map的默认大小,一般不小于256且是2的N次幂
     */
    public EventExecuter(int initialCapacity) {
        //设置默认大小为256
        eventListeners = new HashMap<>(initialCapacity);
        logger = new LoggerManager("EventExecuter-"+counter);
        synchronized (this){
            counter++;
        }
    }

    /**
     * 注册一个事件监听器
     * @param listener 要注册的事件监听器
     */
    public void registerEventListener(@NotNull EventListener listener) {
        Method[] methods = listener.getClass().getDeclaredMethods();

        //注册监听器，如果监听器有注解@HandleEvent的话
        for (Method method : methods) {
            if (method.isAnnotationPresent(HandleEvent.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();

                if (parameterTypes.length == 1) {
                    Class<?> eventType = parameterTypes[0];
                    eventListeners.computeIfAbsent(eventType, k -> new HashMap<>()).put(listener, method);
                }
            }
        }
    }

    /**
     * 解绑一个指定的事件监听器
     * <p>
     * 解绑后该监听器不会再被触发，但事件仍会传递给其他监听器
     * @param listener 要解绑的事件监听器
     */
    public void unregisterEventListener(EventListener listener) {
        for (Map<EventListener, Method> listenerMap : eventListeners.values()) {
            listenerMap.remove(listener);
        }
    }

    /**
     * 解绑所有事件绑定的全部监听器
     * <p>
     * 由系统在程序的结束阶段自动调用
     */
    public void unregisterEventListenerAll(){
        eventListeners.clear();
    }

    /**
     * 触发事件传递
     * <p>
     * 接受一个事件，并传递到所有与该事件绑定的监听器
     * @param event 要传递的事件
     */
    public void dispatchEvent(@NotNull Event event) {
        Class<?> eventType = event.getClass();
        Map<EventListener, Method> listenerMap = eventListeners.get(eventType);
        //事件传递，如果有与该事件绑定的监听器，就触发监听器
        if (listenerMap != null) {
            for (Map.Entry<EventListener, Method> entry : listenerMap.entrySet()) {
                EventListener listener = entry.getKey();
                Method method = entry.getValue();
                try {
                    //如果是异步执行则创建一个新线程来执行
                    if (event.isAsynchronous()){
                        new ThreadRunnable(){
                            @Override
                            public void run(){
                                try {
                                    method.invoke(listener, event);
                                } catch (Exception e) {
                                    logger.error(e.getMessage(),new EventException(e));
                                }
                            }
                        }.runTask(method.getName());
                    }else {
                        method.invoke(listener, event);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),new EventException(e));
                }
            }
        }
    }
}
