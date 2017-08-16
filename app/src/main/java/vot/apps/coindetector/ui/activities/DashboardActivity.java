package vot.apps.coindetector.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import vot.apps.coindetector.R;

public class DashboardActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mToolbar = (Toolbar) findViewById(R.id.dashboard_toolbar);
        mImageView = (ImageView) findViewById(R.id.image_to_display);
        setSupportActionBar(mToolbar);
        Glide.with(this).load(R.drawable.no_image_picture).into(mImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_dashboard_activity, menu);
        MenuItem menuItem = menu.findItem(R.id.action_open_menu);
        menuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        return super.onOptionsItemSelected(menuItem);
    }

}
