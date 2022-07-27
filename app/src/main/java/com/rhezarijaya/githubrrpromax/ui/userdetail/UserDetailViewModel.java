package com.rhezarijaya.githubrrpromax.ui.userdetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rhezarijaya.githubrrpromax.BuildConfig;
import com.rhezarijaya.githubrrpromax.database.FavoriteRepository;
import com.rhezarijaya.githubrrpromax.model.Favorite;
import com.rhezarijaya.githubrrpromax.model.UserDetail;
import com.rhezarijaya.githubrrpromax.util.APIUtil;
import com.rhezarijaya.githubrrpromax.util.SingleEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailViewModel extends ViewModel {
    private final MutableLiveData<List<Favorite>> favoriteList = new MutableLiveData<>();

    private final MutableLiveData<UserDetail> userDetail = new MutableLiveData<>();
    private final MediatorLiveData<UserDetail> userDetailWithFavorite = new MediatorLiveData<>();
    private final MutableLiveData<UserDetail> userSearchData = new MutableLiveData<>();
    private final MutableLiveData<List<UserDetail>> followersList = new MutableLiveData<>();
    private final MutableLiveData<List<UserDetail>> followingsList = new MutableLiveData<>();
    private final MediatorLiveData<List<UserDetail>> followersWithFavorite = new MediatorLiveData<>();
    private final MediatorLiveData<List<UserDetail>> followingsWithFavorite = new MediatorLiveData<>();

    private final MutableLiveData<Boolean> isUserDetailLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFollowersLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFollowingsLoading = new MutableLiveData<>();

    private final MutableLiveData<SingleEvent<String>> userDetailsError = new MutableLiveData<>();
    private final MutableLiveData<SingleEvent<String>> followersError = new MutableLiveData<>();
    private final MutableLiveData<SingleEvent<String>> followingsError = new MutableLiveData<>();

    private final FavoriteRepository favoriteRepository;

    public UserDetailViewModel(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
        this.favoriteList.setValue(new ArrayList<>());
    }

    public LiveData<List<Favorite>> getFavoriteList() {
        return favoriteRepository.getFavorites();
    }

    public LiveData<UserDetail> getUserDetail() {
        return userDetail;
    }

    public LiveData<UserDetail> getUserDetailWithFavorite() {
        return userDetailWithFavorite;
    }

    public LiveData<UserDetail> getUserSearchData() {
        return userSearchData;
    }

    public LiveData<List<UserDetail>> getFollowersList() {
        return followersList;
    }

    public LiveData<List<UserDetail>> getFollowingsList() {
        return followingsList;
    }

    public LiveData<List<UserDetail>> getFollowersWithFavorite() {
        return followersWithFavorite;
    }

    public LiveData<List<UserDetail>> getFollowingsWithFavorite() {
        return followingsWithFavorite;
    }

    public LiveData<Boolean> getIsUserDetailLoading() {
        return isUserDetailLoading;
    }

    public LiveData<Boolean> getIsFollowersLoading() {
        return isFollowersLoading;
    }

    public LiveData<Boolean> getIsFollowingsLoading() {
        return isFollowingsLoading;
    }

    public LiveData<SingleEvent<String>> getUserDetailsError() {
        return userDetailsError;
    }

    public LiveData<SingleEvent<String>> getFollowersError() {
        return followersError;
    }

    public LiveData<SingleEvent<String>> getFollowingsError() {
        return followingsError;
    }

    public void initUserSearchData(UserDetail userDetail) {
        userSearchData.setValue(userDetail);
    }

    public void fetchUserDetail(String username) {
        isUserDetailLoading.setValue(true);

        APIUtil.getAPIService()
                .getUserDetail(BuildConfig.GITHUB_PERSONAL_TOKEN, username)
                .enqueue(new Callback<UserDetail>() {
                    @Override
                    public void onResponse(@NonNull Call<UserDetail> call, @NonNull Response<UserDetail> response) {
                        isUserDetailLoading.setValue(false);

                        if (response.isSuccessful()) {
                            userDetail.setValue(response.body());
                        } else {
                            userDetailsError.setValue(new SingleEvent<>(APIUtil.responseCodeFormatter(response.code())));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserDetail> call, @NonNull Throwable t) {
                        isUserDetailLoading.setValue(false);
                        userDetailsError.setValue(new SingleEvent<>(APIUtil.UNEXPECTED_ERROR));
                    }
                });
    }

    public void fetchFollowers(String username) {
        isFollowersLoading.setValue(true);

        APIUtil.getAPIService()
                .getUserFollowers(com.rhezarijaya.githubrrpromax.BuildConfig.GITHUB_PERSONAL_TOKEN, username)
                .enqueue(new Callback<List<UserDetail>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<UserDetail>> call, @NonNull Response<List<UserDetail>> response) {
                        isFollowersLoading.setValue(false);

                        if (response.isSuccessful()) {
                            followersList.setValue(response.body());
                        } else {
                            followersError.setValue(new SingleEvent<>(APIUtil.responseCodeFormatter(response.code())));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<UserDetail>> call, @NonNull Throwable t) {
                        isFollowersLoading.setValue(false);
                        followersError.setValue(new SingleEvent<>(APIUtil.UNEXPECTED_ERROR));
                    }
                });
    }

    public void fetchFollowings(String username) {
        isFollowingsLoading.setValue(true);

        APIUtil.getAPIService()
                .getUserFollowing(BuildConfig.GITHUB_PERSONAL_TOKEN, username)
                .enqueue(new Callback<List<UserDetail>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<UserDetail>> call, @NonNull Response<List<UserDetail>> response) {
                        isFollowingsLoading.setValue(false);

                        if (response.isSuccessful()) {
                            followingsList.setValue(response.body());
                        } else {
                            followingsError.setValue(new SingleEvent<>(APIUtil.responseCodeFormatter(response.code())));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<UserDetail>> call, @NonNull Throwable t) {
                        isFollowingsLoading.setValue(false);
                        followingsError.setValue(new SingleEvent<>(APIUtil.UNEXPECTED_ERROR));
                    }
                });
    }

    public void insertFavorite(Favorite favorite) {
        favoriteRepository.insert(favorite);
    }

    public void deleteFavorite(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }

    public void mediatorAddSources() {
        userDetailWithFavorite.addSource(getUserDetail(), userDetail -> {
            if (getUserDetail().getValue() != null) {
                UserDetail tempUserDetail = getUserDetail().getValue();
                tempUserDetail.setOnFavorite(
                        FavoriteRepository.isUsernameOnFavorite(
                                Objects.requireNonNull(this.favoriteList.getValue()),
                                tempUserDetail.getLogin())
                );
                userDetailWithFavorite.setValue(tempUserDetail);
            }
        });

        userDetailWithFavorite.addSource(getFavoriteList(), favoriteList -> {
            this.favoriteList.setValue(favoriteList);

            if (getUserDetail().getValue() != null) {
                UserDetail tempUserDetail = getUserDetail().getValue();
                tempUserDetail.setOnFavorite(
                        FavoriteRepository.isUsernameOnFavorite(
                                Objects.requireNonNull(this.favoriteList.getValue()),
                                tempUserDetail.getLogin())
                );
                userDetailWithFavorite.setValue(tempUserDetail);
            }
        });

        followersWithFavorite.addSource(getFollowersList(), userDetails -> {
            if (getFollowersList().getValue() != null) {
                followersWithFavorite.setValue(
                        FavoriteRepository.combineUserDetailsWithFavorites(
                                getFollowersList().getValue(), this.favoriteList.getValue())
                );
            }
        });

        followersWithFavorite.addSource(getFavoriteList(), favoriteList -> {
            this.favoriteList.setValue(favoriteList);

            if (getFollowersList().getValue() != null) {
                followersWithFavorite.setValue(
                        FavoriteRepository.combineUserDetailsWithFavorites(
                                getFollowersList().getValue(), this.favoriteList.getValue())
                );
            }
        });

        followingsWithFavorite.addSource(getFollowingsList(), userDetails -> {
            if (getFollowingsList().getValue() != null) {
                followingsWithFavorite.setValue(
                        FavoriteRepository.combineUserDetailsWithFavorites(
                                getFollowingsList().getValue(), this.favoriteList.getValue())
                );
            }
        });

        followingsWithFavorite.addSource(getFavoriteList(), favoriteList -> {
            this.favoriteList.setValue(favoriteList);

            if (getFollowingsList().getValue() != null) {
                followingsWithFavorite.setValue(
                        FavoriteRepository.combineUserDetailsWithFavorites(
                                getFollowingsList().getValue(), this.favoriteList.getValue())
                );
            }
        });
    }

    public void mediatorRemoveSources() {
        userDetailWithFavorite.removeSource(getUserDetail());
        userDetailWithFavorite.removeSource(getFavoriteList());

        followersWithFavorite.removeSource(getFollowersList());
        followersWithFavorite.removeSource(getFavoriteList());

        followingsWithFavorite.removeSource(getFollowingsList());
        followingsWithFavorite.removeSource(getFavoriteList());
    }
}
