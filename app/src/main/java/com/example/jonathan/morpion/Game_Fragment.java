package com.example.jonathan.morpion;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;


/**
 * A simple {@link Fragment} subclass.
 */
public class Game_Fragment extends Fragment {

    public static Square [][] gridview = new Square[10][10] ;
    public static int current_player = 1 ;
    public static boolean is_morpion = false ;
    public static LinearLayout player_view_1 ;
    public static LinearLayout player_view_2 ;
    public static Context context;
    public static FragmentActivity activity ;
    public static final  ArrayList<Square> winning_square = new ArrayList<Square>() ;
    public static int square_remainder = 100 ; // the amount of square created
    public static int Player_score_1 = 0;
    public static int Player_score_2 = 0 ;
    public static View root_fragment_game_view ;

    public static int sign1 =  Enter_Name_Activity.player_symbol1;
    public static int sign2 =Enter_Name_Activity.player_symbol2;

    //dialog view
    public TextView Player1_score_view ;
    public TextView Player2_score_view ;
    public TextView Player1_name_view ;
    public TextView Player2_name_view ;

    // The last square to be touched
    public static Stack<Square> previous = new Stack<>();


    public Game_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        root_fragment_game_view = inflater.inflate(R.layout.fragment_game_, container, false);
        context = this.getContext();
        activity = this.getActivity() ;
        player_view_1 = (LinearLayout) root_fragment_game_view.findViewById(R.id.gamescreen_player1_container);
        player_view_2 = (LinearLayout) root_fragment_game_view.findViewById(R.id.gamescreen_player2_container);
        sign1 =  Enter_Name_Activity.player_symbol1;
        sign2 =Enter_Name_Activity.player_symbol2;


        //add menu
        setHasOptionsMenu(true);

        createGridsheet(gridview,root_fragment_game_view );
        userLabelInfo(root_fragment_game_view) ;

        animationTurn(current_player);


        return root_fragment_game_view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.game_screen_with_nav, menu);
    }


    /*
This method will handle items selected on toolbar
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.restart) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context) ;
            dialog.setTitle("Restart");
            dialog.setMessage("Are sure you want to restart the game");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do restart here
                    restart();
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do Nothing
                }
            });
            dialog.show() ;

        }
        else if (id == R.id.undo){

            if(!is_morpion){
                if(previous.empty()){
                    Toast.makeText(context, "Unable to undo", Toast.LENGTH_LONG).show();
                }
                else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context) ;
                    dialog.setTitle("Undo");
                    dialog.setMessage("Are sure you want to undo " + getPreviousPlayerName() + "'s move");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            previous.pop().reInitState() ;
                            square_remainder++ ;
                            changeTurn();
                            animationTurn(current_player);



                        }
                    });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Do Nothing
                        }
                    });
                    dialog.show() ;

                }
            }

        }

        else if (id == R.id.scores){
            AlertDialog.Builder dialog = new AlertDialog.Builder(context) ;
            LayoutInflater inflater = this.getLayoutInflater(null);
            View dialogView = inflater.inflate(R.layout.score_dialog_layout, null);
            //
            Player1_name_view = (TextView) dialogView.findViewById(R.id.dialo_player1_name);
            Player2_name_view = (TextView) dialogView.findViewById(R.id.dialo_player2_name);

            //
            Player1_score_view = (TextView) dialogView.findViewById(R.id.dialog_player1_score);
            Player2_score_view = (TextView) dialogView.findViewById(R.id.dialog_player2_score);

            // set names in dialog
            Player1_name_view.setText(Enter_Name_Activity.name1);
            Player2_name_view.setText(Enter_Name_Activity.name2);

            // set score in dialog
            Player1_score_view.setText(String.valueOf(Player_score_1));
            Player2_score_view.setText(String.valueOf(Player_score_2));
            dialog.setView(dialogView);



            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do Undo`
                }
            });
            dialog.show() ;
        }
        return super.onOptionsItemSelected(item);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void createGridsheet(Square[][] matrix, View view){
        GridLayout layout = (GridLayout) view.findViewById(R.id.gridlayout_screen);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float screenWidth = convertPixelsToDp(metrics.widthPixels, this.getContext());
        float screenHeight = convertPixelsToDp(metrics.widthPixels, this.getContext());
        System.out.println ("The width of this screen is =  " + screenWidth);
        System.out.println ("The width of this screen in px is =  " + metrics.widthPixels);
        /*GridLayout.LayoutParams change_Grid_Layout = new GridLayout.LayoutParams();
        change_Grid_Layout.height = (int) appropriateSquareSize(screenHeight) - 32 ;
        layout.setLayoutParams(change_Grid_Layout);*/

        for(int i = 0; i < matrix.length ; i++){
            for(int j = 0; j <matrix[i].length; j++ ){
                Square sqr = new Square(this.getContext(), i, j) ;
                GradientDrawable gd = new GradientDrawable() ;
                gd.setColor(Color.rgb(218, 218, 218));

                GridLayout.LayoutParams sqrlayout = new GridLayout.LayoutParams();
                sqrlayout.width =dpToPx((int)appropriateSquareSize(screenWidth)) ;
                sqrlayout.height =dpToPx((int)appropriateSquareSize (screenWidth)) ;
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
    public void userLabelInfo(View view){
        ImageView iconViewPlayer1 = new ImageView(this.getContext());
        ImageView iconViewPlayer2 = new ImageView(this.getContext());

        Square sqr_player_1 = new Square(this.getContext()) ;
        Square sqr_player_2 = new Square(this.getContext()) ;
        GradientDrawable gd = new GradientDrawable() ;
        gd.setStroke(1, Color.BLACK);
        LinearLayout.LayoutParams sqrlayout = new LinearLayout.LayoutParams(dpToPx(28), dpToPx(28));
        sqrlayout.setMargins(dpToPx(4), dpToPx(2), dpToPx(10), dpToPx(2) );
        sqr_player_1.setLayoutParams(sqrlayout);
        sqr_player_2.setLayoutParams(sqrlayout);

        iconViewPlayer1.setLayoutParams(sqrlayout);
        iconViewPlayer2.setLayoutParams(sqrlayout);
        iconViewPlayer1.setImageResource(sign1);
        iconViewPlayer2.setImageResource(sign2);

        /*
        sqr_player_1.setBackground(gd);
        sqr_player_2.setBackground(gd);
        */
        LinearLayout player1_linearlayout_horizontal = (LinearLayout) view.findViewById(R.id.gamescreen_player1_container);
        LinearLayout player2_linearlayout_horizontal = (LinearLayout) view.findViewById(R.id.gamescreen_player2_container);


        player1_linearlayout_horizontal.addView(iconViewPlayer1, 0);
        player2_linearlayout_horizontal.addView(iconViewPlayer2, 0);
        TextView player_name_1 = (TextView) view.findViewById(R.id.player_name_1) ;
        TextView player_name_2 = (TextView) view.findViewById(R.id.player_name_2) ;
        player_name_1.setText(Enter_Name_Activity.name1);
        player_name_2.setText(Enter_Name_Activity.name2);
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

        //keep track of remainding square
        square_remainder-- ;

        animationTurn(current_player);
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
                    winning_square.clear(); // clear to make sure array doesn't contain any elements ---------- 1
                    count = 1;
                    winning_square.add(gridview[row][column]); // add the first one by default --------------- 2
                    int column_step = column ;
                    while(not_done_checking_horizontally_right){
                        column_step++;
                        if(column_step <= 9 && gridview[row][column_step].state == side){
                            count++ ;
                            winning_square.add(gridview[row][column_step]);// add squares to arraylist-------- 3
                            if(count == 5){
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 1");
                                squareWinAnimation(winning_square, activity); //paint winning squares--------- 4
                                triggerWinningDialogBox(side, 0);
                                break ;
                            }
                        }
                        else{
                            // stop this loop and go check for left
                            not_done_checking_horizontally_right = false ;
                            column_step = column ;
                        }

                    }
                    while(not_done_checking_horizontally_left){
                        column_step-- ;
                        if(column_step >= 0 && gridview[row][column_step].state == side){
                            count++ ;
                            winning_square.add(gridview[row][column_step]);// add squares to arraylist-------- 5
                            if(count == 5){
                                // game is over, stop this process immediatelly
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 2");
                                squareWinAnimation(winning_square, activity); //paint winning squares--------- 6
                                triggerWinningDialogBox(side, 0);
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
                    winning_square.clear(); // clear to make sure array doesn't contain any elements ---------- 1
                    count = 1;
                    winning_square.add(gridview[row][column]); // add the first one by default --------------- 2
                    int row_step = row ;
                    while(not_done_checking_vertically_up){
                        row_step++;
                        if(row_step <= 9 && gridview[row_step][column].state == side){
                            count++ ;
                            winning_square.add(gridview[row_step][column]);// add squares to arraylist-------- 3
                            if(count == 5){
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 3");
                                squareWinAnimation(winning_square, activity); //paint winning squares--------- 4
                                triggerWinningDialogBox(side, 0);
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
                            winning_square.add(gridview[row_step][column]);// add squares to arraylist-------- 5
                            if(count == 5){
                                // game is over, stop this process immediatelly
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 4");
                                squareWinAnimation(winning_square, activity); //paint winning squares--------- 6
                                triggerWinningDialogBox(side, 0);
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
                    winning_square.clear(); // clear to make sure array doesn't contain any elements ---------- 1
                    count = 1;
                    winning_square.add(gridview[row][column]); // add the first one by default --------------- 2
                    int row_step = row ;
                    int column_step = column ;
                    while(not_done_checking_diagonally_up_right){
                        row_step++;
                        column_step--;
                        if( (row_step <= 9 && column_step >= 0) && gridview[row_step][column_step].state == side){
                            count++ ;
                            winning_square.add(gridview[row_step][column_step]);// add squares to arraylist-------- 3
                            if(count == 5){
                                is_morpion = true ;
                                squareWinAnimation(winning_square, activity); //paint winning squares--------- 4
                                triggerWinningDialogBox(side, 0);
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
                        if(row_step >= 0 && column_step <= 9 && gridview[row_step][column_step].state == side){
                            count++ ;
                            winning_square.add(gridview[row_step][column_step]);// add squares to arraylist-------- 5
                            if(count == 5){
                                // game is over, stop this process immediatelly
                                is_morpion = true ;
                                System.out.println("ok it is Morpion here 6");
                                squareWinAnimation(winning_square, activity); //paint winning squares--------- 6
                                triggerWinningDialogBox(side, 0);
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
                    winning_square.clear(); // clear to make sure array doesn't contain any elements ---------- 1
                    count = 1;
                    winning_square.add(gridview[row][column]); // add the first one by default --------------- 2
                    int row_step = row ;
                    int column_step = column ;
                    while(not_done_checking_diagonally_down_right){
                        row_step++;
                        column_step++;

                        if( (row_step <= 9 && column_step <= 9) && gridview[row_step][column_step].state == side){
                            count++ ;
                            winning_square.add(gridview[row_step][column_step]);// add squares to arraylist-------- 3
                            if(count == 5){
                                is_morpion = true ;
                                squareWinAnimation(winning_square, activity); //paint winning squares--------- 4
                                triggerWinningDialogBox(side, 0);
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
                        if((row_step >=0 && column_step >=0) && gridview[row_step][column_step].state == side){
                            // System.out.println("the row_step " + row_step + "the column_step " + column_step);
                                count++ ;
                                winning_square.add(gridview[row_step][column_step]);// add squares to arraylist-------- 5
                                if(count == 5){
                                    // game is over, stop this process immediatelly
                                    is_morpion = true ;
                                    squareWinAnimation(winning_square, activity); //paint winning squares--------- 6
                                    triggerWinningDialogBox(side, 0);
                                    System.out.println("ok it is Morpion here 8");
                                    break ;
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

        //check if it's a draw
        if(square_remainder <= 0){
            triggerWinningDialogBox(side, 1);
        }


        return is_morpion ;

    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void animationTurn(int player){
        //
        LinearLayout player1_text_container = (LinearLayout) root_fragment_game_view.findViewById(R.id.gamescreen_player1_container);
        LinearLayout player2_text_container = (LinearLayout) root_fragment_game_view.findViewById(R.id.gamescreen_player2_container);

        // Get the image containers at index 0
        ImageView player1_icon = (ImageView) player1_text_container.getChildAt(0);
        ImageView player2_icon = (ImageView) player2_text_container.getChildAt(0);


        //animation when is player one's turn
        if(player == 1){
            //remove from the other player
            player2_icon.clearAnimation();
            Animation ani = AnimationUtils.loadAnimation(context,R.anim.blink );
            player1_icon.startAnimation(ani);
        }
        else if(player ==2){
            //remove from the other player
            player1_icon.clearAnimation();
            Animation ani = AnimationUtils.loadAnimation(context,R.anim.blink );
            player2_icon.startAnimation(ani);
        }
        //animation when is player two's turn
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp =  px / metrics.density ;
        return dp;
    }

    public float appropriateSquareSize(float width){
        float result = ((width -32) / 10) - 4 ;
        return result ;
    }

    /*
    This method applies particular properties to Squares, which contributes to the winning strike
    */
    public static void squareWinAnimation(final ArrayList<Square> winning_squares, Activity act){
        // set background to a particular color
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(Square p : winning_squares){

                    p.setBackgroundColor(Color.parseColor("#8BC34A"));
                    Animation ani = AnimationUtils.loadAnimation(context,R.anim.blink );
                    p.startAnimation(ani);

                }
            }
        });



    }



    /*
    This method will restart the game when called
    */
    public static void restart(){

        // clear winning animation if there is a win
        if(is_morpion){
            for(Square p : winning_square){
                p.clearAnimation();
            }
        }


        // need to clean gridview
        for(int i = 0; i < gridview.length ; i++){
            for(int j = 0; j <gridview[i].length; j++ ){
                gridview[i][j].reInitState() ;
            }
        }

        //Set turn to first user
        current_player = 1 ;
        is_morpion = false ;
        square_remainder = 100 ;
        animationTurn(current_player);
    }



    /*
        This method will trigger a dialog box with content based on game's conclusion outcome
        game_status : 1 means that there is a winner
                      2 means that it is a draw

        player : 1 means that player one is the winner
               : 2 means that player two is the winner
     */
    public static void triggerWinningDialogBox(int player, int game_status){
        // Find the name of winner
        CharSequence name = "" ;
        if(player == 1){
            name = Enter_Name_Activity.name1 ;
            // increment count for player 1
            Player_score_1 ++ ;
        }
        else if(player == 2) {
            name = Enter_Name_Activity.name2 ;
            // increment count for player 2
            Player_score_2 ++ ;
        }
        //

        if(game_status == 0){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(context) ;
            dialog.setTitle("We have a winner");
            dialog.setIcon(R.drawable.trophy);
            dialog.setMessage("Congratulations " + name + "\n" +"You won!");
            dialog.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do restart here
                    restart();
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do Nothing
                }
            });
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            dialog.show() ;
                        }
                    }, 2000); // 2000 milliseconds delay

                }
            });

        }
        else if (game_status == 1){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(context) ;
            dialog.setTitle("It's a draw");
            dialog.setIcon(R.drawable.question_mark);
            dialog.setMessage("Tough game, Rematch?");
            dialog.setPositiveButton("Play New game", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do restart here
                    restart();
                }
            });
            dialog.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do Nothing
                }
            });
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            dialog.show() ;
                        }
                    }, 2000); // 2000 milliseconds delay
                }
            });
        }

    }

    public void changeTurn(){
        if(current_player==1){
            current_player = 2 ;
        }
        else{
            current_player = 1;
        }
    }

    public String getPreviousPlayerName(){
        if(current_player ==1){
            return String.valueOf(Enter_Name_Activity.name2)  ;
        }
        else{
            return String.valueOf(Enter_Name_Activity.name1) ;
        }
    }


    public void reset(){

        sign1 =  Enter_Name_Activity.player_symbol1;
        sign2 =Enter_Name_Activity.player_symbol2;
        Square [][] gridview = new Square[10][10] ;
         current_player = 1 ;
          is_morpion = false ;
         ArrayList<Square> winning_square = new ArrayList<Square>() ;
         square_remainder = 100 ; // the amount of square created
         Player_score_1 = 0;
         Player_score_2 = 0 ;

        // The last square to be touched
        Stack<Square> previous = new Stack<>();
    }


}

