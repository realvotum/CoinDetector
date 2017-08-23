package vot.apps.coindetector.ui.presenters;

import android.util.Log;
import org.opencv.android.OpenCVLoader;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import vot.apps.coindetector.ui.screen_contracts.StartScreen;

/**
 * Created by Lap on 2017-08-15.
 */

public class StartPresenter {

    private static final boolean openCVLibLoaded;
    private static final String TAG = "StartPresenter";
    private ScheduledThreadPoolExecutor splashScreen;

    static{
        if(!OpenCVLoader.initDebug()){
            openCVLibLoaded = false;
        }else{
            openCVLibLoaded = true;
        }
    }

    public StartPresenter(){
    }

    public void interact(StartScreen startScreen){
        if(!openCVLibLoaded) {
            startScreen.displayError();
        }

        splashScreen = new ScheduledThreadPoolExecutor(1);
        splashScreen.schedule(() -> {

            if(openCVLibLoaded){
                startScreen.loadDashboard();
            }

            startScreen.exit();

        }, 3500, TimeUnit.MILLISECONDS);
    }

    public void stopInteraction(){
        if(splashScreen != null){
            splashScreen.shutdownNow();
        }
    }

}
