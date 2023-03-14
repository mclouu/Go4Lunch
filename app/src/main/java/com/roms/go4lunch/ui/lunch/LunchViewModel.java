package com.roms.go4lunch.ui.lunch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LunchViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public LunchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lunch fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}