package com.example.jonathan.morpion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;
import android.view.MotionEvent;
import android.view.View;
import com.example.jonathan.morpion.game_screen.* ;

/**
 * Created by owner on 7/18/16.
 */
public class Square extends View {

    /*
    0 means com.example.jonathan.morpion.square has not been touched
    1 means it is assigned to Player 1
    2 means it is assigned to Player 2
    by default it will be set to 0
     */
    int state = 0 ;

    /*
    this variable is boolean type that will identify whether this square has already been taken
     */
    boolean is_tapped = false ;

    //Constructor
    public Square(Context context) {
        super(context);
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int player = game_screen.current_player ;
        if(e.getAction() == MotionEvent.ACTION_DOWN){
            if(player == 1){
                this.setBackgroundColor(Color.BLUE);
                game_screen.current_player = 2 ;
            }
            else{
                this.setBackgroundColor(Color.GREEN);
                game_screen.current_player = 1 ;
            }
        }
        return true ;
    }


}
