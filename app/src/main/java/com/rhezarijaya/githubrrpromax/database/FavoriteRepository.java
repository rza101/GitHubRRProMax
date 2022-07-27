package com.rhezarijaya.githubrrpromax.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rhezarijaya.githubrrpromax.model.Favorite;
import com.rhezarijaya.githubrrpromax.model.UserDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteRepository {
    private static volatile FavoriteRepository INSTANCE;

    private final FavoriteDAO favoriteDAO;
    private final ExecutorService executorService;

    private FavoriteRepository(Application application){
        this.executorService = Executors.newSingleThreadExecutor();
        this.favoriteDAO = AppDatabase.getInstance(application).userDetailDAO();
    }

    public static FavoriteRepository getInstance(Application application){
        if(INSTANCE == null){
            synchronized (FavoriteRepository.class){
                INSTANCE = new FavoriteRepository(application);
            }
        }

        return INSTANCE;
    }

    public LiveData<List<Favorite>> getFavorites(){
        return favoriteDAO.getFavorites();
    }

    public void insert(Favorite favorite) {
        executorService.execute(() -> {
            try {
                favoriteDAO.insertFavorite(favorite);
            } catch (Exception ignored) {}
        });
    }

    public void delete(Favorite favorite){
        executorService.execute(() -> favoriteDAO.deleteFavorite(favorite));
    }

    public static List<UserDetail> combineUserDetailsWithFavorites(List<UserDetail> searchData, List<Favorite> favoriteList) {
        List<UserDetail> combinedList = new ArrayList<>();

        for (UserDetail userDetail : searchData) {
            boolean isOnFavorite = false;

            for(Favorite favorite : favoriteList){
                if(favorite.getUsername().equalsIgnoreCase(userDetail.getLogin())){
                    isOnFavorite = true;
                    break;
                }
            }

            userDetail.setOnFavorite(isOnFavorite);
            combinedList.add(userDetail);
        }

        return combinedList;
    }
}
