package com.example.jonathan.morpion;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class game_screen_with_nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Square [][] gridview = new Square[10][10] ;
    public static int current_player = 1 ;
    public static boolean is_morpion = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen_with_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //draw grid in layout
        createGridsheet(gridview);
        userLabelInfo() ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_screen_with_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void createGridsheet(Square[][] matrix){
        GridLayout layout = (GridLayout) findViewById(R.id.gridlayout_screen);

        for(int i = 0; i < matrix.length ; i++){
            for(int j = 0; j <matrix[i].length; j++ ){
                Square sqr = new Square(this, i, j) ;
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
        TextView player_name_1 = (TextView) findViewById(R.id.player_name_1) ;
        TextView player_name_2 = (TextView) findViewById(R.id.player_name_2) ;
        player_name_1.setText( enter_name_screen.name1);
        player_name_2.setText(enter_name_screen.name2);
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
    /*
    this method will determine whether or not it morpion
    meaning if five squares with the same color are aligned (vertically, horizontally & diagonaly)
    int row - row position of the most recent placed square
    int column - column position of the most recent placed square
    Return type - returns true if it exists such alignmemnt, false if not
     */

    public static boolean isMorpion( final int row, final int column, final int side){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("the row is " + row + " And the column " + column + " with state " + side);
                int count;
                //boolean to check
                boolean not_done_checking_horizontally_right = true;
                boolean not_done_checking_horizontally_left = true;
                boolean not_done_checking_vertically_up = true;
                boolean not_done_checking_vertically_down = true;
                boolean not_done_checking_diagonally_up_right = true;
                boolean not_done_checking_diagonally_up_left = true;
                boolean not_done_checking_diagonally_down_right = true;
                boolean not_done_checking_diagonally_down_left = true;

                //code to check if it is aligned horizontally
                if(!is_morpion){
                    count = 1;
                    int column_step = column ;
                    while(not_done_checking_horizontally_right){
                        column_step++;
                        if(column_step <= 9 && gridview[row][column_step].state == side){
                            count++ ;
                            if(count == 5){
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 1");
                                break ;
                            }
                        }
                        else{
                            // stop this loop and go check for left
                            not_done_checking_horizontally_right = false ;
                            column_step = column ;
                        }

                    }
                    System.out.println("the row is " + row + " And the column " + column + " with state " + side);
                    while(not_done_checking_horizontally_left){
                        column_step-- ;
                        if(column_step >= 0 && gridview[row][column_step].state == side){
                            count++ ;
                            if(count == 5){
                                // game is over, stop this process immediatelly
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 2");
                                break ;
                            }
                        }
                        else{
                            // stop this loop and go check vertically
                            not_done_checking_horizontally_left = false ;
                        }

                    }
                }

                //code to check if it is aligned vertically
                if(!is_morpion){
                    count = 1;
                    int row_step = row ;
                    while(not_done_checking_vertically_up){
                        row_step++;
                        if(row_step <= 9 && gridview[row_step][column].state == side){
                            count++ ;
                            if(count == 5){
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 3");
                                break ;
                            }
                        }
                        else{
                            // stop this loop and go check for left
                            not_done_checking_vertically_up = false ;
                            row_step = row ;
                        }

                    }
                    while(not_done_checking_vertically_down){
                        row_step-- ;
                        if(row_step >= 0 && gridview[row_step][column].state == side){
                            count++ ;
                            if(count == 5){
                                // game is over, stop this process immediatelly
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 4");
                                break ;
                            }
                        }
                        else{
                            // stop this loop and go check vertically
                            not_done_checking_vertically_down = false ;
                        }

                    }
                }

                //code to check if it is aligned diagonally - right
                if(!is_morpion){
                    count = 1;
                    int row_step = row ;
                    int column_step = column ;
                    while(not_done_checking_diagonally_up_right){
                        row_step++;
                        column_step--;
                        if( (row_step <= 9 && column_step >= 0) && gridview[row_step][column_step].state == side){
                            count++ ;
                            if(count == 5){
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 5");
                                break ;
                            }
                        }
                        else{
                            // stop this loop and go check for left
                            not_done_checking_diagonally_up_right = false ;
                            row_step = row ;
                            column_step = column;
                        }

                    }
                    while(not_done_checking_diagonally_up_left){
                        row_step-- ;
                        column_step++;
                        if(row_step >= 0 && column_step <= 9 && gridview[row_step][column].state == side){
                            count++ ;
                            if(count == 5){
                                // game is over, stop this process immediatelly
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 6");
                                break ;
                            }
                        }
                        else{
                            // stop this loop and go check vertically
                            not_done_checking_diagonally_up_left = false ;
                        }

                    }
                }

                //code to check if it is aligned diagonally - left
                if(!is_morpion){
                    count = 1;
                    int row_step = row ;
                    int column_step = column ;
                    while(not_done_checking_diagonally_down_right){
                        row_step++;
                        column_step++;
                        if( (row_step <= 9 && column_step <= 9) && gridview[row_step][column_step].state == side){
                            count++ ;
                            if(count == 5){
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 7");
                                break ;
                            }
                        }
                        else{
                            // stop this loop and go check for left
                            not_done_checking_diagonally_down_right = false ;
                            row_step = row ;
                            column_step = column;
                        }

                    }
                    while(not_done_checking_diagonally_down_left){
                        row_step-- ;
                        column_step--;
                        if((row_step >=0 && column_step >=0)){
                            // System.out.println("the row_step " + row_step + "the column_step " + column_step);
                            if(gridview[row_step][column_step].state == side){
                                count++ ;
                                if(count == 5){
                                    // game is over, stop this process immediatelly
                                    is_morpion = true ;
                                    System.out.println("ok it is Morpion here 8");
                                    break ;
                                }
                            }

                        }
                        else{
                            // stop this loop and go check vertically
                            not_done_checking_diagonally_down_left = false ;
                        }

                    }
                }
            }
        });
        thread.start();







        return is_morpion ;

    }
}
