package com.ankushgrover.imagesearch.ui.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.architecture.BaseFragment;
import com.ankushgrover.imagesearch.ui.listing.ListingViewModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 26/7/18.
 */
public class DetailsFragment extends BaseFragment {

    private ViewPager viewPager;
    private ListingViewModel model;

    public DetailsFragment() {
    }

    public void setViewModel(ListingViewModel model) {
        this.model = model;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewPager = (ViewPager) inflater.inflate(R.layout.fragment_details, container, false);
        viewPager.setAdapter(new DetailsPagerAdapter(getChildFragmentManager(), model.getPhotos()));
        viewPager.setCurrentItem(model.getSelectedItemPosition());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                model.setSelectedItemPosition(position);
            }
        });

        prepareSharedElementTransition();

        postponeEnterTransition();
        return viewPager;


    }

    private void prepareSharedElementTransition() {
        Transition transition =
                TransitionInflater.from(getContext())
                        .inflateTransition(R.transition.image_shared_element_transition);
        setSharedElementEnterTransition(transition);

        setEnterSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {

                        Fragment currentFragment = (Fragment) viewPager.getAdapter()
                                .instantiateItem(viewPager, model.getSelectedItemPosition());
                        View view = currentFragment.getView();
                        if (view == null) {
                            return;
                        }

                        sharedElements.put(names.get(0), view.findViewById(R.id.image));
                    }
                });
    }
}
