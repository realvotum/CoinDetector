package vot.apps.coindetector.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import vot.apps.coindetector.R;
import vot.apps.coindetector.ui.presenters.DashboardPresenter;
import vot.apps.coindetector.ui.screen_contracts.DashboardScreen;

public class DashboardActivity extends AppCompatActivity implements DashboardScreen {

    private Toolbar mToolbar;
    private ImageView mImageView;
    private LinearLayout mLinearLayout;
    private CircularProgressBar mProgressBar;
    private DashboardPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mToolbar = (Toolbar) findViewById(R.id.dashboard_toolbar);
        mImageView = (ImageView) findViewById(R.id.image_to_display);
        mProgressBar = (CircularProgressBar)findViewById(R.id.dashboard_progress_bar);
        mLinearLayout = (LinearLayout)findViewById(R.id.dashoard_linearlayout_progress_bar);
        mPresenter = new DashboardPresenter(this, this, mProgressBar, mLinearLayout, mImageView);
        mPresenter.setProgressBarDimensAndHide();
        setSupportActionBar(mToolbar);
        Glide.with(this).load(R.drawable.no_image_picture)
                .centerCrop()
                .into(mImageView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mPresenter.stopProcessing();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard_activity, menu);
        MenuItem menuItem = menu.findItem(R.id.action_open_menu);
        menuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.action_load_image:
                mPresenter.onChooseImageFromGallery();
                break;
            case R.id.action_start_looking_for_circles:
                mPresenter.startImageProcessingProcedure();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void updateImage(byte[] bitmapByteArray) {
        Glide.with(this)
                .load(bitmapByteArray)
                .fitCenter()
                .into(mImageView);
    }

    @Override
    public void chooseImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select OriginalPicture"), DashboardPresenter.SELECT_PICTURE);
    }

    @Override
    public void showImageFromGalleryLoadingError() {
        Toast.makeText(this, R.string.error_load_image, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        mLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showImage() {
        mImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImage() {
        mImageView.setVisibility(View.GONE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent selectedImage) {
        mPresenter.setImageChosen(requestCode, resultCode, selectedImage);
    }



}

