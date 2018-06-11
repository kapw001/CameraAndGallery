package education.pappayaed.com.mycameraandimage;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by yasar on 14/3/18.
 */

public class MediaTest {

    private MediaPlayer mediaPlayer;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public MediaTest() {
        this.mediaPlayer = new MediaPlayer();
        try {
            this.mediaPlayer.setDataSource("");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
