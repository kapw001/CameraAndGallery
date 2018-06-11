package education.pappayaed.com.mycameraandimage;

import android.media.MediaPlayer;

/**
 * Created by yasar on 14/3/18.
 */

public class MediaPlayerStop implements State {

    private MediaTest mediaPlayer;

    public MediaPlayerStop(MediaTest mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }


    @Override
    public void doAction() {

        mediaPlayer.getMediaPlayer().stop();

    }
}
