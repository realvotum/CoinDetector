package vot.apps.coindetector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.opencv.android.OpenCVLoader;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import vot.apps.coindetector.view.DashboardActivity;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";
    private static final boolean openCVLibLoaded;

    static{
        if(!OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCV loading failed");
            openCVLibLoaded = false;
        }else{
            Log.d(TAG, "OpenCV loaded!");
            openCVLibLoaded = true;
        }
    }

    private ImageView splashScreenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        splashScreenImage = (ImageView) findViewById(R.id.image_view_splash_screen);
        Glide.with(this).load(R.drawable.splash_screen_image).into(splashScreenImage);

        if(!openCVLibLoaded) {
            Toast.makeText(StartActivity.this, R.string.library_init_fail, Toast.LENGTH_LONG).show();
        }

        ScheduledThreadPoolExecutor splashScreen = new ScheduledThreadPoolExecutor(1);
        splashScreen.schedule(() -> {

            if(openCVLibLoaded){
                Intent startApp = new Intent(StartActivity.this,DashboardActivity.class);
                startActivity(startApp);
            }

            StartActivity.this.finish();

        }, 3500, TimeUnit.MILLISECONDS);
    }
}

