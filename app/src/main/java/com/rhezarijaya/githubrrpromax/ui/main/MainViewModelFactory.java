package com.rhezarijaya.githubrrpromax.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rhezarijaya.githubrrpromax.database.FavoriteRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final FavoriteRepository favoriteRepository;

    public MainViewModelFactory(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(favoriteRepository);
    }
}
