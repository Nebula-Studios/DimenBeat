package net.nebula.api.client.audio;

import javazoom.jl.decoder.JavaLayerException;
import net.nebula.api.event.client.Audio.AsyncCreateAudioEvent;
import net.nebula.api.modder.EventAPI;
import net.nebula.client.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

public class Audio {

    //播放器
    private AudioPlayer player = null;
    //UUID，与当前播放器对应
    private final String uuid;
    //音乐名字
    private String name;
    //音频文件路径
    private String path;

    /**
     * 初始化音乐播放类
     * @param name 音乐名字，作为标识
     * @param path 音频文件路径
     */
    public Audio(String name,String path){
        //生成一个唯一UUID标识
        uuid = UUID.randomUUID().toString();
        this.name = name;
        this.path = path;
        init(path);
        //触发事件
        AsyncCreateAudioEvent event = new AsyncCreateAudioEvent(AsyncCreateAudioEvent.class.getSimpleName(),name,uuid);
        EventAPI.eventExecuter.dispatchEvent(event);
    }

    /**
     * 读取音频文件
     * @param path 音频文件的路径
     */
    public void init(String path){
        try {
            //尝试加载音乐
            player = new AudioPlayer(new FileInputStream(path));
        } catch (JavaLayerException | FileNotFoundException e) {
            Main.logger.error(e.getMessage(),e);
            //异常标志
            player = null;
        }
    }

    //音乐播放线程
    Runnable runnable = () -> {
        try {
            player.play();
        } catch (JavaLayerException e) {
            Main.logger.error(e.getMessage(),e);
            player.close();
        }
    };

    /**
     * 播放音频
     */
    public void play(){
        if (player != null) {
            net.nebula.Main.Main.threadExcutor.execute(runnable);
        }
    }

    /**
     * 停止音频
     */
    public void stop(){
        //当音乐播放器还存在时才尝试关闭
        if (player != null) {
            net.nebula.Main.Main.threadExcutor.cancel(runnable);
        }
    }

    /**
     * 释放音频资源
     */
    public void close(){
        if (player != null){
            stop();
            player.close();
            player = null;
        }
    }
}
