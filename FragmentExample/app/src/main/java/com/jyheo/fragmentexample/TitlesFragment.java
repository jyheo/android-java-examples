package com.jyheo.fragmentexample;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jyheo.fragmentexample.databinding.FragmentTitlesBinding;


public class TitlesFragment extends Fragment {

    private FragmentTitlesBinding binding;
    private MyViewModel model;

    public TitlesFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTitlesBinding.inflate(inflater, container, false);

        model = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        binding.listview.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_activated_1, Shakespeare.TITLES));
        binding.listview.setOnItemClickListener((adapterView, view, i, l) -> model.select(i));
        binding.listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        return binding.getRoot();
    }
}
