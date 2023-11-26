package net.nebula.api.client.audio;

import javazoom.jl.decoder.JavaLayerException;
import net.nebula.Main.Main;
import net.nebula.api.GameSystem;
import net.nebula.api.event.client.Audio.AsyncCreateAudioEvent;
import net.nebula.api.event.client.Audio.AsyncStopAudioEvent;
import net.nebula.client.logger.LoggerManager;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

public class Audio {

    //播放器
    private AudioPlayer player = null;
    //UUID，与当前播放器对应
    private final String uuid;
    //音乐名字
    private final String name;
    //音乐播放器线程的状态
    private static boolean status;

    /**
     * 初始化音乐播放类
     * @param name 音乐名字，作为标识
     * @param path 音频文件路径
     */
    public Audio(@NotNull String name, String path){
        //生成一个唯一UUID标识(根据音乐名生成)
        uuid = UUID.nameUUIDFromBytes(name.getBytes()).toString();
        this.name = name;
        //尝试加载音频文件
        init(path);
        //触发事件
        AsyncCreateAudioEvent event = new AsyncCreateAudioEvent(AsyncCreateAudioEvent.class.getSimpleName(),this.name,this.uuid);
        GameSystem.eventExecuter.dispatchEvent(event);
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
            LoggerManager.getLogger().error(e.getMessage(),e);
            //异常标志
            player = null;
        }
    }

    //音乐播放线程
    Runnable runnable = () -> {
        try {
            player.play();
        } catch (JavaLayerException e) {
            LoggerManager.getLogger().error(e.getMessage(),e);
            player.close();
        }
    };

    /**
     * 播放音频
     */
    public void play(){
        if (player != null & !status) {
            Main.coreThreadExcutor.execute(runnable);
            status = true;
        }
    }

    /**
     * 停止音频
     */
    public void stop(){
        //当音乐播放器还存在时才尝试停止
        if (player != null & status) {
            Main.coreThreadExcutor.cancel(runnable);
            status = false;
            //触发事件
            AsyncStopAudioEvent event = new AsyncStopAudioEvent(AsyncStopAudioEvent.class.getSimpleName(),this.name,this.uuid);
            GameSystem.eventExecuter.dispatchEvent(event);
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
