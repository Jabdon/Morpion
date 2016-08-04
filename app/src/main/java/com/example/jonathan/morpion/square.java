package com.example.jonathan.morpion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;
import android.os.AsyncTask;
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
    /*
    position of sqaure in the matrix
     */
    int row_position ;
    int column_position ;

    //Constructor
    public Square(Context context ) {
        super(context);
    }
    public Square(Context context, int row, int column ) {
        super(context);
        this.row_position = row ;
        this.column_position = column ;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(!this.is_tapped){
            int player = game_screen.current_player ;
            if(e.getAction() == MotionEvent.ACTION_DOWN){
                if(player == 1){
                    this.setBackgroundColor(Color.BLUE);
                    this.setState(1);
                    game_screen.current_player = 2 ;
                }
                else{
                    this.setBackgroundColor(Color.GREEN);
                    this.setState(2);
                    game_screen.current_player = 1 ;
                }
            }
            this.is_tapped = true ;
                    game_screen.isMorpion(this.row_position, this.column_position, this.state);
                    //System.out.println(game_screen.thread_morpion.getState());


        }

        return true ;
    }


}
