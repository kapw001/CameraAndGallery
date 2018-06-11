package education.pappayaed.com.mycameraandimage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yasar on 12/3/18.
 */

public class PojoClas {

    private String t;

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }


    public Date getDate() {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(getT());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
