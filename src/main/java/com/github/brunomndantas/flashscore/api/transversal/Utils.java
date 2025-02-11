package com.github.brunomndantas.flashscore.api.transversal;

public class Utils {

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) { }
    }

}
