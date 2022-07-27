package com.rhezarijaya.githubrrpromax.ui.userdetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rhezarijaya.githubrrpromax.database.FavoriteRepository;

public class UserDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final FavoriteRepository favoriteRepository;

    public UserDetailViewModelFactory(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserDetailViewModel(favoriteRepository);
    }
}
