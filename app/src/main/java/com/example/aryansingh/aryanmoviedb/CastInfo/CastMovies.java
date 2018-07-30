
package com.example.aryansingh.aryanmoviedb.CastInfo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CastMovies {

    @SerializedName("cast")
    private List<Casts> mCasts;
    @SerializedName("crew")
    private List<Object> mCrew;
    @SerializedName("id")
    private Long mId;

    public List<Casts> getCast() {
        return mCasts;
    }

    public void setCast(List<Casts> casts) {
        mCasts = casts;
    }

    public List<Object> getCrew() {
        return mCrew;
    }

    public void setCrew(List<Object> crew) {
        mCrew = crew;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

}
