package net.nebula.api.event.client.Audio;

import net.nebula.api.event.Event;

/**
 * 音乐播放器停止播放事件
 * <p>
 * 表示一个音乐播放器停止播放音乐
 * <p>
 * 该事件是异步触发的
 */
public class AsyncStopAudioEvent extends Event {
    //音乐播放器的uuid
    private final String uuid;
    //音乐播放器的音乐名字
    private final String name;

    /**
     * 默认构造函数
     * <p>
     * 生成一个空事件名字,uuid,音乐名字的音乐播放器停止播放事件
     */
    public AsyncStopAudioEvent(){
        super(null,true);
        uuid = null;
        name = null;
    }

    /**
     * 默认构造函数
     * <p>
     * 生成一个指定UUID，音乐名字，事件名字的音乐播放器停止播放事件
     */
    public AsyncStopAudioEvent(String eventName, String audioName, String uuid) {
        super(eventName,true);
        name = audioName;
        this.uuid = uuid;
    }

    /**
     * 返回被停止的音乐名字
     * @return 音乐名字
     */
    public String getAudioName(){
        return name;
    }

    /**
     * 返回音乐播放器的UUID
     * @return UUID
     */
    public String getUUID(){
        return uuid;
    }
}
