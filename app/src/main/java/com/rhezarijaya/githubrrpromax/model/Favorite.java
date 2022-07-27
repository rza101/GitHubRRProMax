package com.rhezarijaya.githubrrpromax.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_favorite")
public class Favorite implements Parcelable {
    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "avatar_url")
    private String avatarUrl;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "added_at")
    private String addedAt;

    public Favorite() {
    }

    protected Favorite(Parcel in) {
        username = in.readString();
        avatarUrl = in.readString();
        userId = in.readInt();
        addedAt = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(avatarUrl);
        dest.writeInt(userId);
        dest.writeString(addedAt);
    }

    @NonNull
    @Override
    public String toString() {
        return "Favorite{" +
                "username='" + username + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", userId=" + userId +
                ", addedAt='" + addedAt + '\'' +
                '}';
    }
}
