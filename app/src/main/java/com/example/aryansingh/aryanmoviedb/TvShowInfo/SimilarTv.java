
package com.example.aryansingh.aryanmoviedb.TvShowInfo;

import java.util.List;

import com.example.aryansingh.aryanmoviedb.TvShows.TvResult;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SimilarTv {

    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private List<TvResult> mResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;

    public Long getPage() {
        return mPage;
    }

    public void setPage(Long page) {
        mPage = page;
    }

    public List<TvResult> getResults() {
        return mResults;
    }

    public void setResults(List<TvResult> results) {
        mResults = results;
    }

    public Long getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Long totalPages) {
        mTotalPages = totalPages;
    }

    public Long getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Long totalResults) {
        mTotalResults = totalResults;
    }

}
