package net.nebula.api.modder;

import net.nebula.api.event.EventExecuter;

public class EventAPI {
    /**
     * 所有事件的 注册/解绑 调度器
     */
    public static volatile EventExecuter eventExecuter = new EventExecuter();
}
