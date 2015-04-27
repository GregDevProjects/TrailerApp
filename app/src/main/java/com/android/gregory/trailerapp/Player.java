package com.android.gregory.trailerapp;

/**
 * Created by Gregory on 4/26/2015.
 */
public class Player {

    private boolean Paused = false;
    int stopPosition = 0;


    public boolean isPaused() {
        return Paused;
    }

    public void setPaused(boolean isPaused) {
        this.Paused = isPaused;
    }


}
