package com.example.jonathan.morpion;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
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
import android.util.TypedValue;
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
        userLabelInfo() ;

    }

    /*
    This method creates the gridlayout view and internal views
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void createGridsheet(Square[][] matrix){
        GridLayout layout = (GridLayout) findViewById(R.id.gridlayout_screen);

        for(int i = 0; i < matrix.length ; i++){
            for(int j = 0; j <matrix[i].length; j++ ){
                Square sqr = new Square(this) ;
                GradientDrawable gd = new GradientDrawable() ;
                gd.setColor(Color.rgb(218, 218, 218));
                GridLayout.LayoutParams sqrlayout = new GridLayout.LayoutParams();
                sqrlayout.width =dpToPx(32) ;
                sqrlayout.height =dpToPx(32) ;
                sqrlayout.setMargins(dpToPx(2), dpToPx(2), dpToPx(2), dpToPx(2) );
                sqr.setLayoutParams(sqrlayout);
                sqr.setBackground(gd);
                matrix[i][j] = sqr ;

                // add it to grid view
                layout.addView(matrix[i][j]);
                //System.out.println("the height for " + "Matrix " + i +"/"+ j +" is " + sqr.getHeight());
            }
        }

    }
    /*
    this method will take care of creating square/circle in front/before
    of name labels
    ONLY for USER vs USER
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void userLabelInfo(){
        Square sqr_player_1 = new Square(this) ;
        Square sqr_player_2 = new Square(this) ;
        GradientDrawable gd = new GradientDrawable() ;
        gd.setStroke(1, Color.BLACK);
        LinearLayout.LayoutParams sqrlayout = new LinearLayout.LayoutParams(dpToPx(32), dpToPx(32));
        sqrlayout.setMargins(dpToPx(20), dpToPx(2), dpToPx(10), dpToPx(2) );
        sqr_player_1.setLayoutParams(sqrlayout);
        sqr_player_2.setLayoutParams(sqrlayout);
        sqr_player_1.setBackground(gd);
        sqr_player_2.setBackground(gd) ;
        LinearLayout player1_linearlayout_horizontal = (LinearLayout) findViewById(R.id.gamescreen_player1_container);
        LinearLayout player2_linearlayout_horizontal = (LinearLayout) findViewById(R.id.gamescreen_player2_container);
        player1_linearlayout_horizontal.addView(sqr_player_1, 0);
        player2_linearlayout_horizontal.addView(sqr_player_2, 0);
    }

    /*
    helper method
     */

    public int dpToPx(int dp){
        Resources r = this.getResources() ;
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );

        return px ;
    }



}
