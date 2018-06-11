package education.pappayaed.com.mycameraandimage;

/**
 * Created by yasar on 14/3/18.
 */

public class MediaONOFF implements State {

    private State state;

//    public MediaONOFF(State state) {
//        this.state = state;
//    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void doAction() {

        this.state.doAction();

    }
}
