package com.roms.go4lunch.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Map fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}