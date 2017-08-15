package vot.apps.coindetector.ui.presenters;


import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import vot.apps.coindetector.R;
import vot.apps.coindetector.ui.activities.DashboardActivity;
import vot.apps.coindetector.ui.activities.StartActivity;
import vot.apps.coindetector.ui.screen_contracts.StartScreen;

/**
 * Created by Lap on 2017-08-15.
 */

public class StartPresenter {

    private static final boolean openCVLibLoaded;
    private static final String TAG = "StartPresenter";

    static{
        if(!OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCV loading failed");
            openCVLibLoaded = false;
        }else{
            Log.d(TAG, "OpenCV loaded!");
            openCVLibLoaded = true;
        }
    }

    public StartPresenter(){
    }

    public void interact(StartScreen startScreen){
        if(!openCVLibLoaded) {
            startScreen.displayError();
        }

        ScheduledThreadPoolExecutor splashScreen = new ScheduledThreadPoolExecutor(1);
        splashScreen.schedule(() -> {

            if(openCVLibLoaded){
                startScreen.loadDashboard();
            }

            startScreen.exit();

        }, 3500, TimeUnit.MILLISECONDS);
    }


}
