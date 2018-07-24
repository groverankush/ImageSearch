package com.ankushgrover.imagesearch.ui.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ankushgrover.imagesearch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPagerFragment extends Fragment {


    public DetailPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_pager, container, false);
    }

}
