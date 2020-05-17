package com.jyheo.fragmentexample;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.jyheo.fragmentexample.databinding.FragmentDetailsBinding;


public class DetailsFragment extends Fragment {
    private FragmentDetailsBinding binding;
    private int mIndex;

    public DetailsFragment() { }
    public DetailsFragment(int idx) { mIndex = idx; }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        binding.textview.setText(Shakespeare.DIALOGUE[mIndex]);
        return binding.getRoot();
    }

}
