package com.ankushgrover.imagesearch.ui.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.architecture.BaseActivity;

public class DetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initView();
    }

    private void initView() {
        ViewPager pager = findViewById(R.id.pager);

    }
}
