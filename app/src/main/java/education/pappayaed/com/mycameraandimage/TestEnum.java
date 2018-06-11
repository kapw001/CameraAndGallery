package education.pappayaed.com.mycameraandimage;

import android.media.MediaPlayer;

/**
 * Created by yasar on 14/3/18.
 */

public class TestEnum {

    enum Test {
        Idle, PLAYING, STOPED
    }

    private Test test;

    private MediaPlayer mediaPlayer;

    public TestEnum() {
        this.mediaPlayer = new MediaPlayer();
    }

    public void start() {

        test = Test.PLAYING;

    }

    public void stop() {

        test = Test.STOPED;

    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    private void setFunction(Test function) {

        switch (function) {

            case PLAYING:

                break;

        }
    }
}
