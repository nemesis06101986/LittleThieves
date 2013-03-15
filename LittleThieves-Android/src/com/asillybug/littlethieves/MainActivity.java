package com.asillybug.littlethieves;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.supercompany.game.TopillosGame;

public class MainActivity extends AndroidApplication {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initialize(new TopillosGame(), false);
    }
}
