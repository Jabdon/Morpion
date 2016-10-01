package com.example.jonathan.morpion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        // start the animation now
        splashAnimation();
    }

    /*
        Handle animation for splash screen then direct the app to option screen
     */

    public void splashAnimation(){
        ImageView logo_image  = (ImageView) findViewById(R.id.Splash_logo);
        Animation ani = AnimationUtils.loadAnimation(this ,R.anim.zoomin);
        logo_image.startAnimation(ani);

        // new thread that will handle switching to next other activity
        Thread startActivity_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplication(), Options_Activity.class) ;
                startActivity(intent);

            }
        });
        startActivity_thread.start();
    }
}
