package net.nebula.api.client.audio;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.InputStream;

public class AudioPlayer extends AdvancedPlayer {
    /**
     * 默认构造函数
     * @param stream 指向音频文件的文件流
     */
    public AudioPlayer(InputStream stream) throws JavaLayerException {
        super(stream);
    }
}
