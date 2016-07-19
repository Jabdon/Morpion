package com.example.jonathan.morpion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;
import android.view.View;

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

    //Constructor
    public Square(Context context) {
        super(context);
    }




}
