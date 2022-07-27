package com.rhezarijaya.githubrrpromax.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse implements Parcelable {

    @SerializedName("total_count")
    private final int totalCount;

    @SerializedName("incomplete_results")
    private final boolean incompleteResults;

    @SerializedName("items")
    private final List<UserDetail> items;

    protected SearchResponse(Parcel in) {
        totalCount = in.readInt();
        incompleteResults = in.readByte() != 0;
        items = in.createTypedArrayList(UserDetail.CREATOR);
    }

    public static final Creator<SearchResponse> CREATOR = new Creator<SearchResponse>() {
        @Override
        public SearchResponse createFromParcel(Parcel in) {
            return new SearchResponse(in);
        }

        @Override
        public SearchResponse[] newArray(int size) {
            return new SearchResponse[size];
        }
    };

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<UserDetail> getItems() {
        return items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalCount);
        dest.writeByte((byte) (incompleteResults ? 1 : 0));
        dest.writeTypedList(items);
    }

    @Override
    public String toString() {
        return "SearchResponse{" +
                "totalCount=" + totalCount +
                ", incompleteResults=" + incompleteResults +
                ", items=" + items +
                '}';
    }
}
