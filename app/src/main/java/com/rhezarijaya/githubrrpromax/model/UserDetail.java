package com.rhezarijaya.githubrrpromax.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class UserDetail implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("login")
    private String login;

    @SerializedName("name")
    private String name;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("public_repos")
    private int publicRepos;

    @SerializedName("followers")
    private int followers;

    @SerializedName("following")
    private int following;

    @SerializedName("followers_url")
    private String followersUrl;

    @SerializedName("following_url")
    private String followingUrl;

    @SerializedName("company")
    private String company;

    @SerializedName("email")
    private String email;

    @SerializedName("location")
    private String location;

    private boolean isOnFavorite;

    public UserDetail() {
    }

    protected UserDetail(Parcel in) {
        id = in.readInt();
        login = in.readString();
        name = in.readString();
        avatarUrl = in.readString();
        publicRepos = in.readInt();
        followers = in.readInt();
        following = in.readInt();
        followersUrl = in.readString();
        followingUrl = in.readString();
        company = in.readString();
        email = in.readString();
        location = in.readString();
        isOnFavorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(login);
        dest.writeString(name);
        dest.writeString(avatarUrl);
        dest.writeInt(publicRepos);
        dest.writeInt(followers);
        dest.writeInt(following);
        dest.writeString(followersUrl);
        dest.writeString(followingUrl);
        dest.writeString(company);
        dest.writeString(email);
        dest.writeString(location);
        dest.writeByte((byte) (isOnFavorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserDetail> CREATOR = new Creator<UserDetail>() {
        @Override
        public UserDetail createFromParcel(Parcel in) {
            return new UserDetail(in);
        }

        @Override
        public UserDetail[] newArray(int size) {
            return new UserDetail[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public boolean isOnFavorite() {
        return isOnFavorite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setPublicRepos(int publicRepos) {
        this.publicRepos = publicRepos;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOnFavorite(boolean onFavorite) {
        isOnFavorite = onFavorite;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", publicRepos=" + publicRepos +
                ", followers=" + followers +
                ", following=" + following +
                ", followersUrl='" + followersUrl + '\'' +
                ", followingUrl='" + followingUrl + '\'' +
                ", company='" + company + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", isOnFavorite=" + isOnFavorite +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetail that = (UserDetail) o;
        return id == that.id && publicRepos == that.publicRepos && followers == that.followers && following == that.following && isOnFavorite == that.isOnFavorite && Objects.equals(login, that.login) && Objects.equals(name, that.name) && Objects.equals(avatarUrl, that.avatarUrl) && Objects.equals(followersUrl, that.followersUrl) && Objects.equals(followingUrl, that.followingUrl) && Objects.equals(company, that.company) && Objects.equals(email, that.email) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, avatarUrl, publicRepos, followers, following, followersUrl, followingUrl, company, email, location, isOnFavorite);
    }
}
