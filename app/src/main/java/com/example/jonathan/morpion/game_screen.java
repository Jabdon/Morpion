package com.example.jonathan.morpion;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.graphics.drawable.GradientDrawable;

public class game_screen extends AppCompatActivity {

    public Square [][] gridview = new Square[10][10] ;
    public static int current_player = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //draw grid in layout
        createGridsheet(gridview);
        LinearLayout game_container = (LinearLayout) findViewById(R.id.game_container);
        //game_container.setBackgroundColor(Color.BLACK);

    }

    // this method will populate button in grid section
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void createGridsheet(Square[][] matrix){
        GridLayout layout = (GridLayout) findViewById(R.id.gridlayout_screen);

        for(int i = 0; i < matrix.length ; i++){
            for(int j = 0; j <matrix[i].length; j++ ){
                Square sqr = new Square(this) ;
                GradientDrawable gd = new GradientDrawable() ;
                gd.setStroke(1, Color.BLACK);
                LinearLayout.LayoutParams sqrlayout = new LinearLayout.LayoutParams(90, 90);
                sqr.setLayoutParams(sqrlayout);
                sqr.setBackground(gd);
                //listenerreturn false;

                matrix[i][j] = sqr ;

                // add it to grid view
                layout.addView(matrix[i][j]);
                //System.out.println("the height for " + "Matrix " + i +"/"+ j +" is " + sqr.getHeight());
            }
        }


        System.out.println("should be added to game screen");

    }



}
