package com.jyheo.fragmentexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private final MutableLiveData<Integer> selected = new MutableLiveData<>();

    public MyViewModel() {
        selected.setValue(-1);
    }

    public void select(Integer item) {
        selected.setValue(item);
    }

    public LiveData<Integer> getSelected() {
        return selected;
    }
}
