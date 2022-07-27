package com.rhezarijaya.githubrrpromax.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rhezarijaya.githubrrpromax.BuildConfig;
import com.rhezarijaya.githubrrpromax.database.FavoriteRepository;
import com.rhezarijaya.githubrrpromax.model.Favorite;
import com.rhezarijaya.githubrrpromax.model.SearchResponse;
import com.rhezarijaya.githubrrpromax.model.UserDetail;
import com.rhezarijaya.githubrrpromax.util.APIUtil;
import com.rhezarijaya.githubrrpromax.util.SingleEvent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<SearchResponse> searchResponse = new MutableLiveData<>();
    private final MutableLiveData<List<Favorite>> favoriteList = new MutableLiveData<>();
    private final MediatorLiveData<List<UserDetail>> userDetailWithFavorite = new MediatorLiveData<>();

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<SingleEvent<String>> error = new MutableLiveData<>();

    private final FavoriteRepository favoriteRepository;

    public MainViewModel(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
        this.favoriteList.setValue(new ArrayList<>());
    }

    public LiveData<SearchResponse> getSearchResponse() {
        return searchResponse;
    }

    public LiveData<List<UserDetail>> getUserDetailWithFavorite() {
        return userDetailWithFavorite;
    }

    public LiveData<List<Favorite>> getFavoriteList() {
        return favoriteRepository.getFavorites();
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<SingleEvent<String>> getError() {
        return error;
    }

    public void mediatorAddSources() {
        userDetailWithFavorite.addSource(getSearchResponse(), searchResponse -> {
            if (getSearchResponse().getValue() != null) {
                userDetailWithFavorite.setValue(
                        FavoriteRepository.combineUserDetailsWithFavorites(
                                getSearchResponse().getValue().getItems(), favoriteList.getValue())
                );
            }
        });

        userDetailWithFavorite.addSource(getFavoriteList(), favorites -> {
            this.favoriteList.setValue(favorites);

            if (getSearchResponse().getValue() != null) {
                userDetailWithFavorite.setValue(
                        FavoriteRepository.combineUserDetailsWithFavorites(
                                getSearchResponse().getValue().getItems(), favoriteList.getValue())
                );
            }
        });
    }

    public void mediatorRemoveSources() {
        userDetailWithFavorite.removeSource(getSearchResponse());
        userDetailWithFavorite.removeSource(getFavoriteList());
    }

    public void search(String query) {
        isLoading.setValue(true);

        APIUtil.getAPIService()
                .getSearch(BuildConfig.GITHUB_PERSONAL_TOKEN, query)
                .enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                        isLoading.setValue(false);

                        if (response.isSuccessful()) {
                            searchResponse.setValue(response.body());
                        } else {
                            error.setValue(new SingleEvent<>(APIUtil.responseCodeFormatter(response.code())));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                        isLoading.setValue(false);
                        error.setValue(new SingleEvent<>(APIUtil.UNEXPECTED_ERROR));
                    }
                });
    }

    public void insertFavorite(Favorite favorite) {
        favoriteRepository.insert(favorite);
    }

    public void deleteFavorite(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }
}
