
package com.example.aryansingh.aryanmoviedb.MovieInfo;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Cast implements Serializable{

    @SerializedName("cast")
    private List<Cast> mCast;
    @SerializedName("cast_id")
    private Long mCastId;
    @SerializedName("character")
    private String mCharacter;
    @SerializedName("credit_id")
    private String mCreditId;
    @SerializedName("crew")
    private List<Crew> mCrew;
    @SerializedName("gender")
    private Long mGender;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("order")
    private Long mOrder;
    @SerializedName("profile_path")
    private String mProfilePath;

    public List<Cast> getCast() {
        return mCast;
    }

    public void setCast(List<Cast> cast) {
        mCast = cast;
    }

    public Long getCastId() {
        return mCastId;
    }

    public void setCastId(Long castId) {
        mCastId = castId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
    }

    public String getCreditId() {
        return mCreditId;
    }

    public void setCreditId(String creditId) {
        mCreditId = creditId;
    }

    public List<Crew> getCrew() {
        return mCrew;
    }

    public void setCrew(List<Crew> crew) {
        mCrew = crew;
    }

    public Long getGender() {
        return mGender;
    }

    public void setGender(Long gender) {
        mGender = gender;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getOrder() {
        return mOrder;
    }

    public void setOrder(Long order) {
        mOrder = order;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

}
