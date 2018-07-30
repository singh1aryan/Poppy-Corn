
package com.example.aryansingh.aryanmoviedb.TvShows;

import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class TvResponse {

    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    public List<TvResult> mTvResults;
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
        return mTvResults;
    }

    public void setResults(List<TvResult> tvResults) {
        mTvResults = tvResults;
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
