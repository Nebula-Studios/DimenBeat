package net.nebula.api.event;

/**
 * 代表所有事件
 * <p>
 * 它是一个最基本的事件
 */
public abstract class Event {
    /**
     * 事件名字
     */
    private final String eventName;
    /**
     * 是否异步执行
     * <p>
     * 为false则代表同步执行
     * <p>
     * 注意:自定义事件应始终异步执行
     */
    private final boolean asynchronous;
    /**
     * 设置事件是否被取消
     * <p>
     * 为true代表已被取消
     */
    private boolean canceled;


    /**
     * 默认构造方法
     * <p>
     * 默认设置事件为同步事件，自定义名字为事件类名字
     */
    public Event(){
        this.eventName = getEventName();
        this.asynchronous = false;
        this.canceled = false;
    }

    /**
     * 默认构造方法
     * <p>
     * 默认设置事件为同步事件
     * @param eventName 设置事件的名字
     */
    public Event(String eventName) {
        this.eventName = eventName;
        this.asynchronous = false;
        this.canceled = false;
    }

    /**
     * 自定义构造方法
     * @param eventName 设置事件的名字
     * @param asynchronous 为true代表事件是异步执行的
     */
    public Event(String eventName, boolean asynchronous) {
        this.eventName = eventName;
        this.asynchronous = asynchronous;
        this.canceled = false;
    }

    /**
     * 获取事件类的简单名字
     * @return 事件类的简单名字
     */
    public String getEventName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 获取事件类的自定义名字
     * @return 自定义名字
     */
    public String getCustomName(){
        return eventName;
    }

    /**
     * 获取事件是否同步
     * @return 为true代表事件是异步执行
     */
    public boolean isAsynchronous() {
        return asynchronous;
    }

    /**
     * 检查事件是否已被取消
     * @return 为true代表事件已被取消
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * 取消事件，做出的大部分修改会被还原
     */
    public void cancel() {
        canceled = true;
    }
}
