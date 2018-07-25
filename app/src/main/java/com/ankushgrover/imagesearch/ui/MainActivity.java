package com.ankushgrover.imagesearch.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.architecture.BaseActivity;
import com.ankushgrover.imagesearch.ui.listing.ListingFragment;
import com.ankushgrover.imagesearch.ui.listing.ListingViewModel;

public class MainActivity extends BaseActivity {

    private ListingViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = ViewModelProviders.of(this).get(ListingViewModel.class);

        ListingFragment fragment = new ListingFragment();
        fragment.setViewModel(model);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }
}
