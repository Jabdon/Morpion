package com.example.jonathan.morpion;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

import java.util.Queue;
import java.util.Stack;

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
    @TargetApi(Build.VERSION_CODES.M)
    public boolean onTouchEvent(MotionEvent e) {
        if(!this.is_tapped && !Game_Fragment.is_morpion){
            int player = Game_Fragment.current_player ;
            if(e.getAction() == MotionEvent.ACTION_DOWN){
                if(player == 1){

                    //this.setBackgroundResource(R.drawable.ic_game_cross);
                    Drawable im = getResources().getDrawable(Game_Fragment.sign1, null) ;
                    this.setForeground(im);
                    this.setState(1);
                    Game_Fragment.current_player = 2 ;
                }
                else{
                    //this.setBackgroundResource(R.drawable.ic_game_circle);
                    Drawable im = getResources().getDrawable(Game_Fragment.sign2, null) ;
                    this.setForeground(im);
                    this.setState(2);
                    Game_Fragment.current_player = 1 ;
                }

                this.is_tapped = true ;
                Game_Fragment.isMorpion(this.row_position, this.column_position, this.state);
                //System.out.println(game_screen.thread_morpion.getState());

                // register the previous square to be touched
                if(!Game_Fragment.previous.isEmpty() && Game_Fragment.previous.size() == 4){
                    Stack<Square> s = new Stack<>();
                    for(int i = 0; i < 3; i++){
                        s.push(Game_Fragment.previous.pop());
                    }
                    //Empty stack
                    Game_Fragment.previous.empty();

                    for(int i = 0; i < 3; i++){
                        Game_Fragment.previous.push(s.pop());
                    }
                    Game_Fragment.previous.push(this) ;
                }
                else {
                    Game_Fragment.previous.push(this) ;
                }
            }



        }

        return true ;
    }

    /*
        This method brings this view or square to original state
     */

    @TargetApi(Build.VERSION_CODES.M)
    public boolean reInitState(){
        //remove foreground image
        this.setForeground(null);
        // reset state
        setState(0);
        //make it tappable again
        is_tapped = false ;

        /*Redraw the background to init color
        When a user wins, square color changes, therefore we need to make sure that this method redraw everything from scrath
         */
        GradientDrawable gd = new GradientDrawable() ;
        gd.setColor(Color.rgb(218, 218, 218));
        setBackground(gd);
        return true;
    }

}
