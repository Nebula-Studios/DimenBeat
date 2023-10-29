package net.nebula.api.client.audio;

import javazoom.jl.decoder.JavaLayerException;
import net.nebula.util.FileUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static net.nebula.client.Main.logger;

/**
 * 音频配置类
 * <p>
 * 致力于简化音频播放，并且尽量保证线程安全
 * @version 2.0
 * @author 3cxc
 */
public class AudioUtils {
    //播放器
    private AudioPlayer player;
    //音频线程
    private Thread thread;
    //列表播放线程
    private Thread thread1;
    //是否同步执行
    private final boolean Aysnc;
    //音乐文件列表
    private static List<String> MusicList = new ArrayList<>();
    //检测Timer是否已经运行完成
    private static boolean isTimerRunning = false;

    /**
     * 默认构造方法，默认是异步的
     * @param player 音频播放器
     */
    public AudioUtils(AudioPlayer player){
        this.player = player;
        this.Aysnc = false;
    }

    /**
     * 默认构造方法，默认是异步的
     * @param list 播放列表,存储着每个音频文件的地址
     */
    public AudioUtils(List<String> list){
        this.Aysnc = false;
        MusicList = list;
    }

    /**
     * 默认构造方法
     * @param player 音频播放器
     * @param aysnc 若设置为true代表同步执行
     */
    public AudioUtils(AudioPlayer player,boolean aysnc){
        this.player = player;
        this.Aysnc = aysnc;
    }
    /**
     * 默认构造方法
     * @param aysnc 若设置为true代表同步执行
     * @param list 播放列表,存储着每个音频文件的地址
     */
    public AudioUtils(List<String> list,boolean aysnc){
        this.Aysnc = aysnc;
        MusicList = list;
    }

    /**
     * 返回所使用的音频播放器
     * @return 音频播放器
     */
    public AudioPlayer getPlayer(){
        return player;
    }

    /**
     * 设置所使用的音频播放器
     * @param player 音频播放器
     */
    public void setPlayer(AudioPlayer player){
        this.player = player;
    }

    /**
     * 播放音频
     */
    public void play() {
        play(0,-1);
    }

    /**
     * 播放音频
     * @param frames 播放多少帧之后就结束(-1表示播放到结束)
     */
    public void play(int frames){
        play(0,frames);
    }

    /**
     * 播放音频
     * @param start 从第几帧开始播放(0则表示从头播放)
     * @param end 播放到第几帧结束(-1则表示播放到结束)
     */
    public void play(int start, int end){
        thread = new Thread(() -> {
            if (player != null){
                try {
                    isTimerRunning = true;
                    player.play(start,end);
                    isTimerRunning = false;
                } catch (JavaLayerException e) {
                    isTimerRunning = false;
                    logger.error(e.getMessage(), e);
                    player.close();//释放音频资源
                }
            }
        });
        thread.start();
        if (Aysnc){//如果是同步的则阻塞
            try {
                thread.join();
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        }
    }

    public void playListAll(){
        //除非音频列表不为空才执行
        if (!MusicList.isEmpty()) {
            thread1 = new Thread(() -> {
                try {
                    for (String s : MusicList) {
                        //尝试读取音频
                        player = new AudioPlayer(new FileInputStream(FileUtils.getDataFolder() + "assets\\music\\menu\\" + s));
                        play();
                        while (true) {
                            //循环检测，如果音乐播放完毕或收到了中断信号就跳出并执行相关操作
                            if (!isTimerRunning) {
                                break;
                            } else if (Thread.currentThread().isInterrupted()) {
                                break;
                            }
                        }
                        if (Thread.currentThread().isInterrupted()) {
                            //如果音乐还在运行，则关闭音频
                            if (isTimerRunning) {
                                thread1.interrupt();
                                isTimerRunning = false;
                            }
                            break;
                        }
                    }
                } catch (JavaLayerException | FileNotFoundException e) {
                    if (isTimerRunning) {
                        thread1.interrupt();
                        isTimerRunning = false;
                    }
                    close();
                    logger.error(e.getMessage(), e);
                }
            });
            thread1.start();
        }
    }

    /**
     * 关闭音频(释放资源)
     * <p>
     * 警告:请在使用了stop()方法再使用此方法！
     * <p>
     * 否则会出现无法正确释放的问题!
     */
    public void close(){
        if (player != null){
            player.close();
        }
    }

    /**
     * 停止音频
     */
    public void stop(){
        if (player != null){
            //传递中断信号到音频线程
            if (MusicList.isEmpty()){
                thread1.interrupt();
            }else {
                if (isTimerRunning){
                    try {
                        thread.wait();
                        close();
                    } catch (InterruptedException ignored) {
                        close();
                    }
                }
            }
        }
    }
}
