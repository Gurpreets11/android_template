package com.preet.androidtemplate.factory;



import com.preet.androidtemplate.features.auth.AuthRepository;
import com.preet.androidtemplate.viewmodel.AuthViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Object repository;

    public ViewModelFactory(Object repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel((AuthRepository) repository);
        }
        /*if (modelClass.isAssignableFrom(GarminViewModel.class)) {
            return (T) new GarminViewModel((GarminRepository) repository);
        }*/
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

