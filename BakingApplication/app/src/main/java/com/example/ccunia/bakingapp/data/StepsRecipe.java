package com.example.ccunia.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CCunia on 6/26/2018.
 */

public class StepsRecipe implements Parcelable {
    private final String TAG = StepsRecipe.class.getSimpleName();
    int id;
    String shortDescription;
    String description;
    String videoURL;
    String thumbnailURL;

    public StepsRecipe(){}

    protected StepsRecipe(Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Creator<StepsRecipe> CREATOR = new Creator<StepsRecipe>() {
        @Override
        public StepsRecipe createFromParcel(Parcel in) {
            return new StepsRecipe(in);
        }

        @Override
        public StepsRecipe[] newArray(int size) {
            return new StepsRecipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    public int getStepId() {
        return id;
    }

    public void setStepId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String description) {
        this.shortDescription = description;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getVideoUrl() {
        return videoURL;
    }

    public void setVideoUrl(String url) {
        this.videoURL = url;
    }
    public String getThumbnailUrl() {
        return thumbnailURL;
    }

    public void setThumbnailUrl(String url) {
        this.thumbnailURL = url;
    }
}
