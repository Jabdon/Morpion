package com.example.jonathan.morpion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.adapter.SimpleWheelAdapter;
import com.wx.wheelview.common.WheelData;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;

public class Enter_Name_Activity extends AppCompatActivity {
    public static CharSequence name1 ;
    public static CharSequence name2 ;
    public static int player_symbol1 = R.drawable.ic_game_cross ;
    public static int player_symbol2 = R.drawable.ic_game_circle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    public void initWheel(View view){

        String [] values = {"Cross", "Circle", "Star","Loader" };
        WheelView picker = (WheelView) view.findViewById(R.id.picker);
        ArrayList<WheelData> list_of_images = new ArrayList<>();
        WheelData cross = new WheelData();
        cross.setId(R.drawable.ic_game_cross);
        WheelData circle = new WheelData();
        circle.setId(R.drawable.ic_game_circle);
        WheelData loading = new WheelData();
        loading.setId(R.drawable.ic_action_loading);
        WheelData heart = new WheelData();
        heart.setId(R.drawable.ic_action_heart);
        WheelData star = new WheelData();
        star.setId(R.drawable.ic_action_star);
        list_of_images.add(cross); // cross is 1
        list_of_images.add(circle); // circle is 2
        list_of_images.add(loading); // loading is 3
        list_of_images.add(star); // star is 4
        list_of_images.add(heart); // heart is 5
        picker.setWheelAdapter(new SimpleWheelAdapter(this));
        picker.setWheelSize(3);
        picker.setSkin(WheelView.Skin.Holo);
        picker.setLoop(true);
        picker.setWheelData(list_of_images);

    }

    public void playNowClick(View view) {
        EditText nameEditText1 = (EditText) findViewById(R.id.editText1);
        EditText nameEditText2 = (EditText) findViewById(R.id.editText2);

        if(isNameAcceptable(nameEditText1) && isNameAcceptable(nameEditText2)){
            name1 = nameEditText1.getText() ;
            name2 = nameEditText2.getText() ;


            Intent intent = new Intent(this, Game_Activity.class) ;
            startActivity(intent);
        }
    }

    public boolean isNameAcceptable(EditText text){

        Editable name = text.getText() ;
        if(name.length() == 0 ){
            // create a snackbar that name is null
            Toast.makeText(Enter_Name_Activity.this, "Names cannot be empty", Toast.LENGTH_LONG).show();
            return false ;
        }
        else if (name.length() < 3){
            // create a snackbar that name must be more than 3 letters
            Toast.makeText(Enter_Name_Activity.this, "Name must be more than 3 letters", Toast.LENGTH_LONG).show();
            return false ;
        }
        else{
            System.out.println("this is the string " + name);
            return true ;
        }
    }

    public void selectSign(final View view) {
        final ImageButton button = (ImageButton) view;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this) ;
        /*
        LinearLayout dialogView = (LinearLayout) findViewById(R.id.select_sign_dialog_container);
        */
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.select_sign_dialog, null);
        initWheel(dialogView);
        dialog.setView(dialogView);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do Undo`
                WheelView picker = (WheelView) dialogView.findViewById(R.id.picker);
                int sign = picker.getCurrentPosition();

                if(sign == 0){
                    button.setImageResource(R.drawable.ic_game_cross);
                    if(view.getId() == R.id.spinner1){
                        player_symbol1 = R.drawable.ic_game_cross ;
                    }
                    else {
                        player_symbol2 = R.drawable.ic_game_cross ;
                    }
                }
                else if(sign == 1){
                    button.setImageResource(R.drawable.ic_game_circle);
                    if(view.getId() == R.id.spinner1){
                        player_symbol1 = R.drawable.ic_game_circle ;
                    }
                    else {
                        player_symbol2 = R.drawable.ic_game_circle ;
                    }

                }
                else if(sign == 2){
                    button.setImageResource(R.drawable.ic_action_loading);
                    if(view.getId() == R.id.spinner1){
                        player_symbol1 = R.drawable.ic_action_loading ;
                    }
                    else {
                        player_symbol2 = R.drawable.ic_action_loading ;
                    }
                }
                else if(sign == 3){
                    button.setImageResource(R.drawable.ic_action_star);
                    if(view.getId() == R.id.spinner1){
                        player_symbol1 = R.drawable.ic_action_star ;
                    }
                    else {
                        player_symbol2 = R.drawable.ic_action_star ;
                    }
                }
                else if(sign == 4){
                    button.setImageResource(R.drawable.ic_action_heart);
                    if(view.getId() == R.id.spinner1){
                        player_symbol1 = R.drawable.ic_action_heart ;
                    }
                    else {
                        player_symbol2 = R.drawable.ic_action_heart ;
                    }
                }




            }
        });
        dialog.show() ;

    }
}
