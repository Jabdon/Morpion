package com.example.jonathan.morpion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class enter_name_screen extends AppCompatActivity {
    public static CharSequence name1 ;
    public static CharSequence name2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void playNowClick(View view) {
        EditText nameEditText1 = (EditText) findViewById(R.id.editText1);
        EditText nameEditText2 = (EditText) findViewById(R.id.editText2);

        if(isNameAcceptable(nameEditText1) && isNameAcceptable(nameEditText2)){
            name1 = nameEditText1.getText() ;
            name2 = nameEditText2.getText() ;
            Intent intent = new Intent(this, game_screen_with_nav.class) ;
            startActivity(intent);
        }
    }

    public boolean isNameAcceptable(EditText text){

        Editable name = text.getText() ;
        if(name.length() == 0 ){
            // create a snackbar that name is null
            Toast.makeText(enter_name_screen.this, "Names cannot be empty", Toast.LENGTH_LONG).show();
            return false ;
        }
        else if (name.length() < 3){
            // create a snackbar that name must be more than 3 letters
            Toast.makeText(enter_name_screen.this, "Name must be more than 3 letters", Toast.LENGTH_LONG).show();
            return false ;
        }
        else{
            System.out.println("this is the string " + name);
            return true ;
        }
    }
}
