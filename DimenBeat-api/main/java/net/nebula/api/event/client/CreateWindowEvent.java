package net.nebula.api.event.client;

import net.nebula.api.event.Event;

/**
 * 窗口创建事件
 */
public class CreateWindowEvent extends Event {

    //窗口名字
    private final String WindowName;

    /**
     * 默认构造函数
     * <p>
     * 生成一个空WindowName的初始化窗口事件
     */
    public CreateWindowEvent(){
        WindowName = null;
    }

    /**
     * 默认构造函数
     * <p>
     * 生成一个指定事件名字和窗口名字的初始化窗口事件
     */
    public CreateWindowEvent(String eventName, String windowName) {
        super(eventName,false);
        WindowName = windowName;
    }

    /**
     * 返回窗口名字
     * @return 窗口名字
     */
    public String getWindowName() {
        return WindowName;
    }
}
