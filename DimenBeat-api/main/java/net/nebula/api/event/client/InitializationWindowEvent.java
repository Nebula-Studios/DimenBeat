package net.nebula.api.event.client;

import net.nebula.api.event.Event;

public class InitializationWindowEvent extends Event {

    private final String WindowName;

    /**
     * 默认构造函数
     * <p>
     * 生成一个空WindowName的初始化窗口事件
     */
    public InitializationWindowEvent(){
        WindowName = null;
    }

    public InitializationWindowEvent(String eventName,String windowName) {
        super(eventName);
        WindowName = windowName;
    }

    public InitializationWindowEvent(String eventName, boolean asynchronous, String windowName){
        super(eventName,asynchronous);
        WindowName = windowName;
    }

    public String getWindowName() {
        return WindowName;
    }
}
