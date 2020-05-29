package com.jyheo.fragmentexample;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jyheo.fragmentexample.databinding.FragmentDetailsBinding;


public class DetailsFragment extends Fragment {
    private FragmentDetailsBinding binding;

    public DetailsFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        MyViewModel model = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        model.getSelected().observe(this, idx -> {
            if (idx >= 0)
                binding.textview.setText(Shakespeare.DIALOGUE[idx]);
        });
    }
}
