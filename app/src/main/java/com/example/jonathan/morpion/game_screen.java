package com.example.jonathan.morpion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class game_screen extends AppCompatActivity {

    public Square [][] gridview = new Square[10][10] ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //draw grid in layout
        createGridsheet(gridview);

    }

    // this method will populate button in grid section
    protected void createGridsheet(Square[][] matrix){
        //Create gridview
        GridLayout layout = new GridLayout(this);
        layout.setColumnCount(10);
        layout.setRowCount(10);
        layout.setLayoutParams(new ViewGroup.LayoutParams(-1 , -1));
        for(int i = 0; i < matrix.length ; i++){
            for(int j = 0; j <matrix[i].length; j++ ){
                Square sqr = new Square(this) ;
                // add it to grid view
                layout.addView(matrix[i][j]);
            }
        }
        //add this in the main view
        LinearLayout game_container = (LinearLayout) findViewById(R.id.game_container);
        game_container.addView(layout);

    }

}
