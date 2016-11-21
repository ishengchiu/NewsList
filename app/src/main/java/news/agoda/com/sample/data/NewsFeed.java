package news.agoda.com.sample.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chiu_mac on 2016/11/12.
 */

public class NewsFeed {
    @SerializedName("status")
    private String mStatus;
    @SerializedName("copyright")
    private String mCopyright;
    @SerializedName("section")
    private String mSection;
    @SerializedName("last_updated")
    private String mLastUpdated;
    @SerializedName("num_results")
    private int mNumResults;
    @SerializedName("results")
    private List<NewsEntity> mNewsEntityList = new ArrayList<>();

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String copyright) {
        this.mCopyright = copyright;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        this.mSection = section;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.mLastUpdated = lastUpdated;
    }

    public int getNumResults() {
        return mNumResults;
    }

    public void setNumResults(int numResults) {
        this.mNumResults = numResults;
    }

    public List<NewsEntity> getNewsEntityList() {
        return mNewsEntityList;
    }

    public void addNewsEntity(NewsEntity newsEntity) {
        this.mNewsEntityList.add(newsEntity);
    }

}
