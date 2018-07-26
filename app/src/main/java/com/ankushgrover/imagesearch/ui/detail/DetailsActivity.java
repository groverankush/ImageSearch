package com.ankushgrover.imagesearch.ui.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.architecture.BaseActivity;
import com.ankushgrover.imagesearch.data.model.photo.Photo;

import java.util.ArrayList;

public class DetailsActivity extends BaseActivity {

    public static final String PHOTOS = "photos";
    public static final String SELECTED_PHOTO_INDEX = "selectedIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
    }

    private void initView() {

        ArrayList<Photo> photos = getIntent().getParcelableArrayListExtra(PHOTOS);

        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new DetailsPagerAdapter(getSupportFragmentManager(), photos));
        pager.setCurrentItem(getIntent().getIntExtra(SELECTED_PHOTO_INDEX, 0));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
