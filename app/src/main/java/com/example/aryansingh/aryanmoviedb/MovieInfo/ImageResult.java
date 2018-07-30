
package com.example.aryansingh.aryanmoviedb.MovieInfo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ImageResult {

    @SerializedName("backdrops")
    private List<Object> mBackdrops;
    @SerializedName("id")
    private Long mId;
    @SerializedName("posters")
    private List<Object> mPosters;

    public List<Object> getBackdrops() {
        return mBackdrops;
    }

    public void setBackdrops(List<Object> backdrops) {
        mBackdrops = backdrops;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<Object> getPosters() {
        return mPosters;
    }

    public void setPosters(List<Object> posters) {
        mPosters = posters;
    }

}
