package vot.apps.coindetector.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import vot.apps.coindetector.R;
import vot.apps.coindetector.ui.presenters.StartPresenter;
import vot.apps.coindetector.ui.screen_contracts.StartScreen;

public class StartActivity extends AppCompatActivity implements StartScreen {

    private ImageView splashScreenImage;
    private StartPresenter mStartPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        splashScreenImage = (ImageView) findViewById(R.id.image_view_splash_screen);
        Glide.with(this).load(R.drawable.splash_screen_image).into(splashScreenImage);
        mStartPresenter = new StartPresenter();
    }

    @Override
    public void onStart(){
        super.onStart();
        mStartPresenter.interact(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mStartPresenter.stopInteraction();
    }

    @Override
    public void loadDashboard() {
        Intent startApp = new Intent(StartActivity.this,DashboardActivity.class);
        startActivity(startApp);
    }

    @Override
    public void displayError() {
        Toast.makeText(StartActivity.this, R.string.library_init_fail, Toast.LENGTH_LONG).show();
    }

    @Override
    public void exit() {
        StartActivity.this.finish();
    }
}