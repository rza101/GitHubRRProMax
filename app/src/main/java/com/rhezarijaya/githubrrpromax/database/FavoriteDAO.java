package com.rhezarijaya.githubrrpromax.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.rhezarijaya.githubrrpromax.model.Favorite;

import java.util.List;

@Dao
public interface FavoriteDAO {
    @Query("SELECT * FROM tbl_favorite ORDER BY username ASC")
    LiveData<List<Favorite>> getFavorites();

    @Insert()
    void insertFavorite(Favorite favorite) throws Exception;

    @Delete()
    void deleteFavorite(Favorite favorite);
}
