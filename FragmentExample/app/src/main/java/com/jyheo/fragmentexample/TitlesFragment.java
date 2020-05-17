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

import com.jyheo.fragmentexample.databinding.FragmentTitlesBinding;


public class TitlesFragment extends Fragment {

    private int mCurCheckPosition = -1;
    private FragmentTitlesBinding binding;
    private OnTitleSelectedListener mTitleSelectedListener;

    public TitlesFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTitlesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public interface OnTitleSelectedListener {
        void onTitleSelected(int i, boolean restoreSaved);
    }

    void setOnTitleSelectedListener(OnTitleSelectedListener listener) {
        mTitleSelectedListener = listener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.listview.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_activated_1, Shakespeare.TITLES));
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCurCheckPosition = i;
                if (mTitleSelectedListener != null)
                    mTitleSelectedListener.onTitleSelected(i, false);
            }
        });

        binding.listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        if (savedInstanceState != null)
            mCurCheckPosition = savedInstanceState.getInt("curChoice", -1);

        binding.listview.setSelection(mCurCheckPosition);
        binding.listview.smoothScrollToPosition(mCurCheckPosition);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mCurCheckPosition >= 0 && mTitleSelectedListener != null)
            mTitleSelectedListener.onTitleSelected(mCurCheckPosition, true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }
}
