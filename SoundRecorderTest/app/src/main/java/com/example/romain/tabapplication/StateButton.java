package com.example.romain.tabapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageButton;



/**
 * Created by romain on 03/07/2016.
 */
public class StateButton extends Button {


    private static final int[] STATE_PLAYING = {R.attr.state_playing};
    private static final int[] STATE_PAUSE = {R.attr.state_pause};

    private boolean mIsPlaying = false;

    public StateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
        public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace+2);

        if (mIsPlaying) {
            mergeDrawableStates(drawableState, STATE_PLAYING);
        }
        else {
            mergeDrawableStates(drawableState, STATE_PAUSE);
        }
        return drawableState;
    }

    public void setPlaying(boolean isPlaying) {mIsPlaying = isPlaying;}


}
