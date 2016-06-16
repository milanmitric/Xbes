package hello.entity;

import java.io.Serializable;

/**
 * Created by aloha on 10-Jun-16.
 */
public class Sednica implements Serializable {

    private boolean isItNow;

    public Sednica() {}

    public Sednica(boolean isItNow) {
        this.isItNow = isItNow;
    }


    public boolean isItNow() {
        return isItNow;
    }

    public void setIsItNow(boolean itNow) {
        this.isItNow = itNow;
    }


}
