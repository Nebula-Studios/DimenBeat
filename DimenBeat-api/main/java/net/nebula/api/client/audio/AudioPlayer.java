package net.nebula.api.client.audio;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.InputStream;

public class AudioPlayer extends AdvancedPlayer {
    public AudioPlayer(InputStream stream) throws JavaLayerException {
        super(stream);
    }
}
