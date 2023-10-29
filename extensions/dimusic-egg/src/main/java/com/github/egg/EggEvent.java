package com.github.egg;

import net.nebula.api.event.EventListener;
import net.nebula.api.event.HandleEvent;
import net.nebula.api.event.client.InitializationWindowEvent;

public class EggEvent implements EventListener {
    @HandleEvent(InitializationWindowEvent.class)
    public void egg(InitializationWindowEvent event){
        Egg.api.getLogger().info("好家伙，这个窗口名字好神奇: "+event.getWindowName());
    }
}
